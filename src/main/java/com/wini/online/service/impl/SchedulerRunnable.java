package com.wini.online.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.b3log.solo.model.Article;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.baidu.bae.api.memcache.BaeMemcachedClient;
import com.wini.online.constant.Crawl;
import com.wini.online.model.ArticleExt;
import com.wini.online.service.ICrawlService;
import com.wini.online.service.ISchedulerRunnable;
import com.wini.online.util.DateUtil;
import com.wini.online.util.FetchUtil;

public class SchedulerRunnable implements Runnable, ISchedulerRunnable {

private static final Logger LOGGER = Logger.getLogger(SchedulerRunnable.class.getName());
	
	private JSONObject aCrawlJson;
	private ICrawlService crawlService;
	private BaeMemcachedClient mc;
	
	public SchedulerRunnable(JSONObject aCrawlJson,ICrawlService crawlService) throws Exception {
		this.aCrawlJson = aCrawlJson;
		this.crawlService = crawlService;
		this.mc = new BaeMemcachedClient();
	}
	
	@Override
	public void run() {
		try{
			String url = aCrawlJson.getString(Crawl.CRAWL_URL);
			String endOfDate = mc.get(aCrawlJson.getString(Crawl.CRAWL_BASEURL)) == null ? aCrawlJson.getString(Crawl.CRAWL_ENDOFDATE) : (String)mc.get(aCrawlJson.getString(Crawl.CRAWL_BASEURL));
			LOGGER.log(Level.INFO,"正在抓取 " + url + " 中截止日期为" + endOfDate + "的信息");
			crawl(url,endOfDate);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,e.getMessage(), e);
		}
	}
	
	@Override
	public void crawl(String url,String crawlEnd) throws Exception{
		if(url.isEmpty()){
			throw new Exception("url不能为空");
		}
		String content = FetchUtil.get(url);
		if(content.isEmpty()){
			throw new Exception("url无内容返回");
		}
		Document doc = Jsoup.parse(content);
		Elements articles = doc.select(aCrawlJson.getString(Crawl.CRAWL_LIST));
		for(Element obj : articles){
			String href = obj.select(aCrawlJson.getString(Crawl.CRAWL_HREFOFLIST)).attr("href");
			String articleUrl = aCrawlJson.getString(Crawl.CRAWL_BASEURL) + href;
			Map<String,Object> article = crawlContent(articleUrl,crawlEnd);
			if(article.isEmpty()){
				return;
			}else{
				try{
					Element abstractP = obj.select(aCrawlJson.getString(Crawl.CRAWL_ABSTRACTOFLIST)).get(1);
					abstractP.select("a").remove();
					abstractP.select("time").remove();
					article.put(Article.ARTICLE_ABSTRACT,abstractP.toString());
					article.put(ArticleExt.ARTICLE_FROM_URL, articleUrl);
					article.put(ArticleExt.ARTICLE_FROM_NAME, aCrawlJson.getString(Crawl.CRAWL_NAME));
				}catch(Exception e){
					LOGGER.log(Level.SEVERE,"获取简介失败",e);
				}
				article.put(Article.ARTICLE_SIGN_ID,aCrawlJson.getString(Crawl.CRAWL_ARTICLESIGN));
				this.crawlService.crawl(article);
			}
		}
		Element next = doc.select(aCrawlJson.getString(Crawl.CRAWL_NEXTPAGE)).get(0);
		crawl(next.attr("href"),crawlEnd);
	}
	
	@Override
	public Map<String,Object> crawlContent(String url,String crawlEnd) throws Exception{
		Map<String,Object> article = new ConcurrentHashMap<String,Object>();
		if(url.isEmpty()){
			throw new Exception("url不能为空");
		}
		String content = FetchUtil.get(url);
		if(content.isEmpty()){
			throw new Exception("url无内容返回");
		}
		Document doc = Jsoup.parse(content);
		JSONObject articleJson = aCrawlJson.getJSONObject(Crawl.CRAWL_ARTICLE);
		Element element;
		try{
			element = doc.select(articleJson.getString(Crawl.CRAWL_ARTICLE_DATE)).get(0);
			String date = element.text().trim();
			if(crawlEnd.compareTo(date) >= 0){
				return article;
			}else{
				String newCrawlEnd =   mc.get(aCrawlJson.getString(Crawl.CRAWL_BASEURL)) == null ? crawlEnd : (String)mc.get(aCrawlJson.getString(Crawl.CRAWL_BASEURL));
				mc.add(aCrawlJson.getString(Crawl.CRAWL_BASEURL), newCrawlEnd.compareTo(date) > 0 ? newCrawlEnd : date);
				article.put(Article.ARTICLE_CREATE_DATE, DateUtil.str2Date(date + ":00", DateUtil.YYYY_MM_DD_HH_MM_SS));
				article.put(ArticleExt.ARTICLE_FROM_CREATE_DATE, date);
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"获取文章日期失败["+url+"]",e);
		}
		try {
			element = doc.select(articleJson.getString(Crawl.CRAWL_ARTICLE_TITLE)).get(0);
			article.put(Article.ARTICLE_TITLE, element.text() + "[转]");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"获取文章标题失败["+url+"]",e);
		}
		try {
			element = doc.select(articleJson.getString(Crawl.CRAWL_ARTICLE_AUTHOR)).get(0);
			article.put(ArticleExt.ARTICLE_FROM_AUTHOR, element.text().trim());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"获取文章作者失败["+url+"]",e);
		}
		try {
			Elements tags = doc.select(articleJson.getString(Crawl.CRAWL_ARTICLE_TAGS));
			String str = "";
			for(Element tag : tags){
				str = str + tag.text() + ",";
			}
			article.put(Article.ARTICLE_TAGS_REF, str.substring(0, str.lastIndexOf(",") < 0 ? 0 : str.lastIndexOf(",")));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"获取文章标签失败["+url+"]",e);
		}
		
		/*try {
			element = doc.select(articleJson.getString(Crawl.CRAWL_ARTICLE_IMAGE)).get(0);
			article.put(ArticleExt.ARTICLE_CONTENT_IMG,element.attr("src"));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"获取文章插图失败["+url+"]",e);
		}*/
		
		try {
			element = doc.select(articleJson.getString(Crawl.CRAWL_ARTICLE_CONTENT)).get(0);
			article.put(Article.ARTICLE_CONTENT, element.toString());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"获取文章内容失败["+url+"]",e);
		}
		return article;
	}

}

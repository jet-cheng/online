package com.wini.online.service.impl;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Article;
import org.b3log.solo.service.ArticleMgmtService;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.wini.online.constant.Constant;
import com.wini.online.model.ArticleExt;
import com.wini.online.repository.IArticleRepository;
import com.wini.online.service.ICrawlService;
import com.wini.online.util.WiniUtil;

@Service
public class MysqlServiceImpl implements ICrawlService {
	
	private static final Logger LOGGER = Logger.getLogger(MysqlServiceImpl.class.getName());
	
	@Inject
	private ArticleMgmtService articleMgmtService;
	
	@Inject
	private IArticleRepository articleRepository;

	
	@Override
	public synchronized void  crawl(Map<String,Object> obj) throws Exception {
		BasicDBObject object = new BasicDBObject(obj);
		DBObject findone = articleRepository.getArticleByFromUrl(object.getString(ArticleExt.ARTICLE_FROM_URL));
		if (findone != null) {
			LOGGER.log(Level.INFO,"[" + object.getString(Article.ARTICLE_TITLE) + "]已经在数据库中");
			return;
		}
		articleRepository.addArticle(object);
		
		JSONObject article = new JSONObject(obj);
		String articleContent = article.getString(Article.ARTICLE_CONTENT);
		String articleFromName = article.getString(ArticleExt.ARTICLE_FROM_NAME);
		String articleFromAuthor = article.getString(ArticleExt.ARTICLE_FROM_AUTHOR);
		String articleFromCreateDate = article.getString(ArticleExt.ARTICLE_FROM_CREATE_DATE);
		String articleFromUrl = article.getString(ArticleExt.ARTICLE_FROM_URL);
		String[] params = new String[]{articleContent,articleFromName,articleFromAuthor,articleFromCreateDate,articleFromUrl};
		
		article.put(Article.ARTICLE_CONTENT, WiniUtil.get(Constant.WINI_ONLINE_ARTICLE_ABSTRACT, params));
		article.remove(ArticleExt.ARTICLE_FROM_AUTHOR);
		article.remove(ArticleExt.ARTICLE_FROM_CREATE_DATE);
		article.remove(ArticleExt.ARTICLE_FROM_URL);
		article.remove(ArticleExt.ARTICLE_FROM_NAME);
		article.put(Article.ARTICLE_AUTHOR_EMAIL, Constant.WINI_ONLINE_USER_EMAIL);
		article.put(Article.ARTICLE_IS_PUBLISHED, true);
		article.put(Article.ARTICLE_COMMENTABLE, true);
		article.put(Article.ARTICLE_VIEW_PWD, "");
		articleMgmtService.addArticle(new JSONObject().put(Article.ARTICLE, article));
		
	}
	
	
}

package com.wini.online.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wini.online.constant.Crawl;
import com.wini.online.service.impl.MysqlServiceImpl;
import com.wini.online.service.impl.SchedulerRunnable;
import com.wini.online.util.CrawlUtil;

@RequestProcessor
public class SchedulerProcessor {

	private LatkeBeanManager beanManager;
	
	@RequestProcessing(value = "/crawl", method = HTTPRequestMethod.GET)
	public void crawlProcessor(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		beanManager = Lifecycle.getBeanManager();
		JSONArray sites = CrawlUtil.getJsonArray();
		for(int i = 0;i<sites.length();i++){
			JSONObject site = sites.getJSONObject(i);
			SchedulerRunnable runnable = new SchedulerRunnable(site,beanManager.getReference(MysqlServiceImpl.class));
			new Thread(runnable).start();
			response.getWriter().write(site.getString(Crawl.CRAWL_NAME) + "的数据正在抓取..........");
			response.getWriter().close();
		}
	}

}

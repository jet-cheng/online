package com.wini.online.service.impl;

import java.util.Map;

import javax.inject.Inject;

import org.b3log.latke.service.annotation.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.wini.online.model.ArticleExt;
import com.wini.online.repository.IArticleRepository;
import com.wini.online.service.ICrawlService;

@Service
public class MongdbServiceImpl implements ICrawlService{
	
	@Inject
	private IArticleRepository articleRepository;
	

	@Override
	public synchronized void crawl(Map<String,Object> obj) throws Exception {
		BasicDBObject object = new BasicDBObject(obj);
		DBObject findone = articleRepository.getArticleByFromUrl(object.getString(ArticleExt.ARTICLE_FROM_URL));
		if (findone != null) {
			articleRepository.removeArticle(findone);
		}
		articleRepository.addArticle(object);
	}

}

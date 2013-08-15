package com.wini.online.repository.impl;

import org.b3log.latke.repository.annotation.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.wini.online.constant.Constant;
import com.wini.online.model.ArticleExt;
import com.wini.online.repository.IArticleRepository;
import com.wini.online.util.WiniUtil;

@Repository
public class HuxiuRepositoryImpl implements IArticleRepository{
	
	private DBCollection dbCol;
	
	public HuxiuRepositoryImpl() throws Exception {
		MongodbClient mongodb = MongodbClient.getInstance();
		mongodb.db(WiniUtil.get(Constant.WINI_MONGODB_DB));
		this.dbCol = mongodb.collection(WiniUtil.get(Constant.WINI_MONGODB_COL_HUXIU));
	}
	
	@Override
	public DBObject getArticleByFromUrl(String articleFromUrl) throws Exception {
		return this.dbCol.findOne(new BasicDBObject(ArticleExt.ARTICLE_FROM_URL, articleFromUrl));
	}

	@Override
	public WriteResult removeArticle(DBObject article) throws Exception {
		return this.dbCol.remove(article);
	}

	@Override
	public WriteResult addArticle(DBObject article) throws Exception {
		return this.dbCol.insert(article);
	}

}

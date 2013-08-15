package com.wini.online.repository;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public interface IArticleRepository {
	
	public DBObject getArticleByFromUrl(String articleFromUrl) throws Exception;
	
	public WriteResult removeArticle(DBObject article) throws Exception;
	
	public WriteResult addArticle(DBObject article) throws Exception;

}

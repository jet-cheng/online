package com.wini.online.repository.impl;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.b3log.latke.Latkes;
import org.b3log.latke.RuntimeEnv;

import com.baidu.bae.api.util.BaeEnv;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.wini.online.constant.Constant;
import com.wini.online.util.WiniUtil;

public class MongodbClient {
	
	private static final Logger LOGGER = Logger.getLogger(MongodbClient.class.getName());
	
	private static MongodbClient mongoUtil = null;
	public static MongodbClient getInstance(){
		if(mongoUtil == null){
			mongoUtil = new MongodbClient();
		}
		return mongoUtil;
	}
	
	
	private String server;
	private String port;
	private String user;
	private String password;
	private MongoClient mongoClient;
	private DB db;
	private DBCollection collection;
	
	private MongodbClient(){
		try {
			if(Latkes.getRuntimeEnv().equals(RuntimeEnv.BAE)){
				this.server = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_MONGO_IP);
				this.port  = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_MONGO_PORT);
				this.user = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
				this.password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
			}else{
				this.server = WiniUtil.get(Constant.WINI_MONGODB_NOBAE_SERVER);
				this.port = WiniUtil.get(Constant.WINI_MONGODB_NOBAE_PORT);
				this.user = WiniUtil.get(Constant.WINI_MONGODB_NOBAE_USER);
				this.password = WiniUtil.get(Constant.WINI_MONGODB_NOBAE_PASSWORD);
			}
			String host = server + ":" + port;
			this.mongoClient = new MongoClient(new ServerAddress(host),new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE,"初始化mongo客户端失败", e);
		}
	}
	
	public DB db(String databaseName) throws Exception{
		if(this.mongoClient == null){
			throw new Exception("mongoClient 不能为null");
		}
		this.db = this.mongoClient.getDB(databaseName);
		this.db.authenticate(this.user, password.toCharArray());
		return this.db;
	}
	
	public DBCollection collection(String collectionName) throws Exception{
		if(this.db == null){
			throw new Exception("db不能为null");
		}
		this.collection = this.db.getCollection(collectionName);
		return this.collection;
	}
	
}

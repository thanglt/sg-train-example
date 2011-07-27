package com.m3958.firstgwt.service;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDbCacheService implements CacheService{
	
	//ip + db名称可以唯一确定数据的位置。
	
	private DB mongoCacheDb;
	
	private static String DB_NAME = "mongoCacheDb";
	
	public MongoDbCacheService(){
		Mongo m;
		try {
			m = new Mongo("127.0.0.1" , 27017 );
			setMongoCacheDb(m.getDB(DB_NAME));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String get(String key, boolean isFinalKey) {
		return null;
	}

	@Override
	public boolean put(String key, boolean isFinalKey, String content) {
		return false;
	}

	public void setMongoCacheDb(DB mongoCacheDb) {
		this.mongoCacheDb = mongoCacheDb;
	}

	public DB getMongoCacheDb() {
		return mongoCacheDb;
	}

	@Override
	public boolean put(String key, boolean isFinalKey, CacheFor cf,
			Reason reason) {
		return false;
	}

}

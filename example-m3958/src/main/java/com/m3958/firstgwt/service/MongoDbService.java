package com.m3958.firstgwt.service;

import java.net.UnknownHostException;

import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;


@Singleton
public class MongoDbService {
	
	private DB assetsDb;
	
	private GridFS gfs;
	
	private static String DB_NAME = "assetsDb";
	
	public MongoDbService(){
		Mongo m;
		try {
			m = new Mongo("127.0.0.1" , 27017 );
			setAssetsDb(m.getDB( DB_NAME ));
			setGfs(new GridFS(getAssetsDb()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setAssetsDb(DB assetsDb) {
		this.assetsDb = assetsDb;
	}


	public DB getAssetsDb() {
		return assetsDb;
	}


	public void setGfs(GridFS gfs) {
		this.gfs = gfs;
	}


	public GridFS getGfs() {
		return gfs;
	}
}

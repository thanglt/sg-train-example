package com.m3958.firstgwt.service;

public interface CacheService {
	
	public static enum CacheFor{
		
	}
	
	public static enum Reason{
		
	}
	
	boolean put(String key,boolean isFinalKey,String content);
	String get(String key,boolean isFinalKey);
	boolean put(String key,boolean isFinalKey,CacheFor cf,Reason reason);
}

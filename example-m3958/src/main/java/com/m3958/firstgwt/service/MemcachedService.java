package com.m3958.firstgwt.service;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class MemcachedService {
	 static {
	        String[] serverlist = { "localhost:11211"};
	        SockIOPool pool = SockIOPool.getInstance();
	        pool.setServers(serverlist);
	        pool.initialize();
	 }
	 
	 private MemCachedClient mc;
	 
	 
	 @Inject
	 private MySHAService sha;
	 
	 
	 public MemcachedService(){
		 setMc(new MemCachedClient());
	 }

	public void setMc(MemCachedClient mc) {
		this.mc = mc;
	}

	public MemCachedClient getMc() {
		return mc;
	}
	
	
	
	private static String LGB_FETCH = "LgbFetch";

	public String getLgbs(String departmentids,String queryString) {
		queryString = queryString.replaceAll("&_componentId=.*?&", "");
		String key = LGB_FETCH + departmentids + sha.encrypt(queryString);
		return (String) mc.get(key);
	}

	public void setLgbs(String departmentids,String queryString, String v) {
		queryString = queryString.replaceAll("&_componentId=.*?&", "");
		String key = LGB_FETCH + departmentids + sha.encrypt(queryString);
		if(mc.get(departmentids) == null){
			mc.set(departmentids, key);
		}else{
			String t = (String) mc.get(departmentids);
			mc.replace(departmentids, t + "|" + key);
		}
		mc.set(key, v);
	}

	public void clear(String departmentids) {
		String keys = (String) mc.get(departmentids);
		if(keys == null)return;
		for(String key:keys.split("\\|")){
			mc.delete(key);
		}
	}

}

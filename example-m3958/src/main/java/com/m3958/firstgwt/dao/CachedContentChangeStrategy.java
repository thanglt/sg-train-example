package com.m3958.firstgwt.dao;


import com.m3958.firstgwt.model.CachedContent;

public class CachedContentChangeStrategy implements ModelChangeStrategy<CachedContent> {
	

	@Override
	public boolean extraPersistTask(CachedContent model){
		return true;
	}
	

	@Override
	public boolean extraRemoveTask(CachedContent model) {
		return true;
	}
	
	
	@Override
	public boolean extraUpdateTask(CachedContent model,CachedContent newModel){
		return true;
	}

	@Override
	public boolean afterPersist(CachedContent model) {
		return false;
	}
}

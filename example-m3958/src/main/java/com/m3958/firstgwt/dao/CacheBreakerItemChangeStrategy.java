package com.m3958.firstgwt.dao;



import com.m3958.firstgwt.model.CacheBreakerItem;

public class CacheBreakerItemChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<CacheBreakerItem> {
	

	@Override
	public boolean extraPersistTask(CacheBreakerItem model){
		return true;
	}
	

	@Override
	public boolean extraRemoveTask(CacheBreakerItem model) {
		return true;
	}
	
	
	@Override
	public boolean extraUpdateTask(CacheBreakerItem model,CacheBreakerItem newModel){
		return true;
	}

	@Override
	public boolean afterPersist(CacheBreakerItem model) {
		return true;
	}
}

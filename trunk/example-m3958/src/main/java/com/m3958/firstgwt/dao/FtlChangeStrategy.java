package com.m3958.firstgwt.dao;

import java.util.Date;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.Ftl;

public class FtlChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<Ftl> {
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	@Override
	public boolean extraPersistTask(Ftl model){
		if(model.getUpdatedAt() == null){
			model.setUpdatedAt(new Date());
		}
		return true;
	}


	@Override
	public boolean extraRemoveTask(Ftl model) {
		return true;
		
	}

	@Override
	public boolean extraUpdateTask(Ftl model,Ftl newModel){
		model.setUpdatedAt(new Date());
		return true;
	}


	@Override
	public boolean afterPersist(Ftl model) {
		return false;
	}
	

}

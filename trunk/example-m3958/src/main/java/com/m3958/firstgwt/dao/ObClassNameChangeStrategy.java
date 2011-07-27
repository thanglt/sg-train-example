package com.m3958.firstgwt.dao;


import com.m3958.firstgwt.model.ObjectClassName;
import com.m3958.firstgwt.service.ModelAndDao;



public class ObClassNameChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<ObjectClassName> {
	
	@Override
	public boolean extraPersistTask(ObjectClassName model) {
		reloadConfig();	
		return true;
	}

	@Override
	public boolean extraUpdateTask(ObjectClassName model,ObjectClassName newModel){
		reloadConfig();
		return true;
	}
	
	@Override
	public boolean extraRemoveTask(ObjectClassName model) {
		reloadConfig();
		return true;
	}
	
	private boolean reloadConfig(){
		ModelAndDao mc = injector.getInstance(ModelAndDao.class);
		mc.reload();
		return true;
	}

	@Override
	public boolean afterPersist(ObjectClassName model) {
		return false;
	}
	}

	

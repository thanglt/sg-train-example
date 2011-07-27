package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.MenuLevel;

public class MenuLevelDao  extends BaseDao<MenuLevel>{
	
	@Inject
	public MenuLevelDao(MenuLevelChangeStrategy task) {
		super(MenuLevel.class);
		extraStrategyTask = task;
	}
	
	
	public MenuLevel findByName(String name){
		try {
			String qs = "SELECT m FROM MenuLevel AS m WHERE m.name = :name";
			Query q = emp.get().createQuery(qs);
			q.setParameter("name", name);
			return (MenuLevel) q.getSingleResult();
		} catch (Exception e) {}
		
		return null;
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}


	@Override
	public List<MenuLevel> smartNamedQueryFetch() {
		return null;
	}


	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}






}

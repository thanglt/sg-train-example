package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.WebHost;

public class WebHostDao  extends BaseDao<WebHost>{
	
	@Inject
	public WebHostDao(WebHostChangeStrategy extraTask) {
		super(WebHost.class);
		this.extraStrategyTask = extraTask;
	}
	
	
	public WebHost findByName(String name){
		Query q = emp.get().createNamedQuery(WebHost.NamedQueries.FIND_BY_NAME);
		q.setParameter(1, name);
		try {
			WebHost wh = (WebHost) q.getSingleResult();
			return wh;
		} catch (Exception e) {
			return null;
		}
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
	public List<WebHost> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

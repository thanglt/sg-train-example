package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.WebSite;

public class WebSiteDao  extends BaseDao<WebSite>{
	
	@Inject
	public WebSiteDao(WebSiteChangeStrategy tasks) {
		super(WebSite.class);
		extraStrategyTask = tasks;
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
	public List<WebSite> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

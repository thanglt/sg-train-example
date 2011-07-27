package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.SiteConfig;

public class SiteConfigDao  extends BaseDao<SiteConfig> {

	@Inject
	public SiteConfigDao(SiteConfigChangeStrategy task) {
		super(SiteConfig.class);
		extraStrategyTask = task;
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
	public List<SiteConfig> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}


}

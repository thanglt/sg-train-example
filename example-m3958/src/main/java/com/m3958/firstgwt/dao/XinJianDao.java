package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.XinJian;

public class XinJianDao  extends BaseDao<XinJian>{
	
	@Inject
	public XinJianDao(XinJianChangeStrategy task) {
		super(XinJian.class);
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
	public List<XinJian> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

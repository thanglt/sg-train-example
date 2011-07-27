package com.m3958.firstgwt.dao;


import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Career;

public class CareerDao  extends BaseDao<Career> {
	
	@Inject
	public CareerDao() {
		super(Career.class);
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
	public List<Career> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

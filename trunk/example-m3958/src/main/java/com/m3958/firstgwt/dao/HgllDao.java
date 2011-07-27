package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Hgll;

public class HgllDao extends BaseDao<Hgll>{
	
	public HgllDao() {
		super(Hgll.class);
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<Hgll> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

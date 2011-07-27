package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.House;

public class HouseDao  extends BaseDao<House>{


	@Inject
	public HouseDao() {
		super(House.class);
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
	public List<House> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}





}

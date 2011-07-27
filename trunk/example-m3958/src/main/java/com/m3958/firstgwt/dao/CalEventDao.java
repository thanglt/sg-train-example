package com.m3958.firstgwt.dao;


import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.CalEvent;

public class CalEventDao  extends BaseDao<CalEvent>{

	public CalEventDao() {
		super(CalEvent.class);
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
	public List<CalEvent> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}





}

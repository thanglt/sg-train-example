package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.HelpMessage;

public class HelpMessageDao  extends BaseDao<HelpMessage>{
	
	@Inject
	public HelpMessageDao(HelpMessageChangeStrategy task) {
		super(HelpMessage.class);
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
	public List<HelpMessage> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}





}

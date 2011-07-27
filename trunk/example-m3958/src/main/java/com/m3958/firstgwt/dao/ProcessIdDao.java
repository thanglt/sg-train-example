package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.ProcessId;

public class ProcessIdDao extends BaseDao<ProcessId>{

	@Inject
	public ProcessIdDao() {
		super(ProcessId.class);
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
	public List<ProcessId> smartNamedQueryFetch() {
		return null;
	}


	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}



}

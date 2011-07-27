package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.FtlHistory;

public class FtlHistoryDao  extends BaseDao<FtlHistory>{

	public FtlHistoryDao() {
		super(FtlHistory.class);
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
	public List<FtlHistory> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}



}

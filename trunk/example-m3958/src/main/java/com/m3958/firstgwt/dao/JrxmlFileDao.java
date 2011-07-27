package com.m3958.firstgwt.dao;


import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.JrxmlFile;

public class JrxmlFileDao  extends BaseDao<JrxmlFile> {
	

	@Inject
	public JrxmlFileDao(JrxmlFileChangeStrategy task) {
		super(JrxmlFile.class);
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
	public List<JrxmlFile> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

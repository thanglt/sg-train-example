package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.PageMistake;
import com.m3958.firstgwt.model.WebHost;

public class PageMistakeDao  extends BaseDao<PageMistake>{
	
	@Inject
	public PageMistakeDao(PageMistakeChangeStrategy extraTask) {
		super(PageMistake.class);
		this.extraStrategyTask = extraTask;
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
	public List<PageMistake> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

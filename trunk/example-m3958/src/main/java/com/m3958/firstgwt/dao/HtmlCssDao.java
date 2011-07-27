package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.HtmlCss;

public class HtmlCssDao  extends BaseDao<HtmlCss>{
	
	@Inject
	public HtmlCssDao(HtmlCssChangeStrategy task) {
		super(HtmlCss.class);
		this.extraStrategyTask = task;
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
	public List<HtmlCss> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}




}

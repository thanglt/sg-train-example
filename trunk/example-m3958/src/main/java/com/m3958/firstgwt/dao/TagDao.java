package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Tag;

public class TagDao  extends BaseDao<Tag>{

	public TagDao() {
		super(Tag.class);
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
	public List<Tag> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}





}

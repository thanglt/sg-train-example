package com.m3958.firstgwt.dao;



import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.StepCareer;

public class StepCareerDao  extends BaseDao<StepCareer> {
	
	@Inject
	public StepCareerDao() {
		super(StepCareer.class);
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
	public List<StepCareer> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}






}

package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Reward;

public class RewardDao  extends BaseDao<Reward>{
	
	@Inject
	public RewardDao() {
		super(Reward.class);
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
	public List<Reward> smartNamedQueryFetch() {

		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {

		return null;
	}






}

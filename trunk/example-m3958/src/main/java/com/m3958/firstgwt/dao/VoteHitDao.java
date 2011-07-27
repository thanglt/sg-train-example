package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.VoteHit;
import com.m3958.firstgwt.model.WebHost;

public class VoteHitDao  extends BaseDao<VoteHit>{
	
	@Inject
	public VoteHitDao(VoteHitChangeStrategy extraTask) {
		super(VoteHit.class);
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
	public List<VoteHit> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Vote;


public class VoteDao  extends BaseDao<Vote> {
	
	@Inject
	public VoteDao(VoteChangeStrategy task) {
		super(Vote.class);
		extraStrategyTask = task;
	}
	
	public List<Vote> getTopVotes(int siteId){
		Query q = emp.get().createNamedQuery(Vote.NamedQueries.FIND_TOP);
		q.setParameter("siteId", siteId);
		return q.getResultList();
	}
	
	public List<Vote> getUserVotes(int userId){
		String qs = "SELECT v FROM Vote AS v WHERE v.creator.id = :creatorId AND v.parent IS NULL";
		Query q = emp.get().createQuery(qs);
		q.setParameter("creatorId", userId);
		return q.getResultList();
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
	public List<Vote> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.CacheBreakerItem;

public class CacheBreakerItemDao extends BaseDao<CacheBreakerItem>{
	
	@Inject
	public CacheBreakerItemDao(CacheBreakerItemChangeStrategy task) {
		super(CacheBreakerItem.class);
		this.extraStrategyTask = task;
	}
	
	public List<CacheBreakerItem> getQuequedItem(int siteId,boolean done){
		String qs = "SELECT c FROM CacheBreakerItem AS c WHERE c.siteId = :siteId AND c.done = :done";
		Query q = emp.get().createQuery(qs);
		q.setParameter("siteId", siteId);
		q.setParameter("done", done);
		return q.getResultList();
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<CacheBreakerItem> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

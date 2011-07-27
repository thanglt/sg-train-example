package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Link;


public class LinkDao  extends BaseDao<Link> {
	
	@Inject
	public LinkDao(LinkChangeStrategy task) {
		super(Link.class);
		extraStrategyTask = task;
	}
	
	public List<Link> getTopLinks(int siteId){
		Query q = emp.get().createNamedQuery(Link.NamedQueries.FIND_TOP);
		q.setParameter("siteId", siteId);
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
	public List<Link> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

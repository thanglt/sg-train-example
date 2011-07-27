package com.m3958.firstgwt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;


import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.PageHit;
import com.m3958.firstgwt.utils.SpecialDate;
import com.wideplay.warp.persist.Transactional;

public class PageHitDao  extends BaseDao<PageHit>{
	
	@Inject
	public PageHitDao() {
		super(PageHit.class);
	}
	
	
	@Transactional
	public void addPageHit(int siteId,String obname,String obId){
		String us = "UPDATE PageHit AS p SET p.hitNum = p.hitNum + 1 WHERE p.siteId = :siteId AND " +
				"p.obname = :obname AND p.obId = :obId AND p.createdAt >= :startOfDay";
		Query q = emp.get().createQuery(us);
		q.setParameter("siteId", siteId);
		q.setParameter("obname", obname);
		q.setParameter("obId", obId);
		q.setParameter("startOfDay", new SpecialDate().startOfDay());
		int i = q.executeUpdate();
		if(i == 0){
			PageHit ph = new PageHit();
			ph.setSiteId(siteId);
			ph.setObname(obname);
			ph.setObId(obId);
			ph.setCreatedAt(new Date());
			ph.setHitNum(1);
			smartPersistBaseModel(ph);
		}
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
	public List<PageHit> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

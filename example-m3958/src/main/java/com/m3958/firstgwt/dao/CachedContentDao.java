package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.CachedContent;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.wideplay.warp.persist.Transactional;

public class CachedContentDao  extends BaseDao<CachedContent>{
	
	@Inject
	public CachedContentDao(CachedContentChangeStrategy task) {
		super(CachedContent.class);
		this.extraStrategyTask = task;
	}
	
	@Transactional
	public void removeItemByOid(String obName,int obid){
		String qs = "DELETE FROM CachedContent cc WHERE cc.obName = :obName AND cc.obid = :obid ";
		Query q = emp.get().createQuery(qs);
		q.setParameter("obName", obName);
		q.setParameter("obid", obid);
		q.executeUpdate();
	}
	
	@Transactional
	public void removeAll(WebSite ws){
		for(WebHost wh : ws.getWebhosts()){
			String qs = "DELETE FROM CachedContent cc WHERE cc.hostName = :hostName";
			Query q = emp.get().createQuery(qs);
			q.setParameter("hostName", wh.getName());
			q.executeUpdate();
		}
	}
	
	@Transactional
	public void removeItemByTpl(String hostName,String tplName){
		String qs = "DELETE FROM CachedContent cc WHERE cc.hostName = :hostName AND cc.tplName = :tplName ";
		Query q = emp.get().createQuery(qs);
		q.setParameter("hostName", hostName);
		q.setParameter("tplName", tplName);
		q.executeUpdate();
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
	public List<CachedContent> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

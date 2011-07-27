package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.m3958.firstgwt.client.errorhandler.SmartDuplicateRecordException;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.FetchUrl;
import com.wideplay.warp.persist.Transactional;

public class FetchUrlDao  extends BaseDao<FetchUrl> {

	public FetchUrlDao() {
		super(FetchUrl.class);
	}
	
	@Transactional
	public void setLastUrl(String modelName,String queryString) throws SmartDuplicateRecordException {
		FetchUrl fu = null;
		fu = findByModelName(modelName);
		if(fu == null){
			fu = new FetchUrl();
			fu.setQueryString(queryString);
			fu.setModelName(modelName);
			smartPersistBaseModel(fu);
		}else{
			fu.setQueryString(queryString);
			update(fu);
		}
		
	}

	@SuppressWarnings("unchecked")
	public FetchUrl findByModelName(String modelName) {
		String qs = "SELECT q FROM FetchUrl q WHERE q.modelName = :mn";
		Query q = emp.get().createQuery(qs);
		q.setParameter("mn", modelName);
		List<FetchUrl> results = q.getResultList();
		if(results.size()>0){
			return results.get(0);
		}else{
			return null;
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
	public List<FetchUrl> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}




}

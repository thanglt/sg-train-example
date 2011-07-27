package com.m3958.firstgwt.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.ObjectClassName;
import com.m3958.firstgwt.service.ModelAndDao;

public class ObjectClassNameDao  extends BaseDao<ObjectClassName> {
	
	@Inject
	public ObjectClassNameDao(ObClassNameChangeStrategy task) {
		super(ObjectClassName.class);
		this.extraStrategyTask = task;
	}
	
	public ObjectClassName getObjectClassNameByEname(String ename){
		String qs = "SELECT o FROM ObjectClassName AS o WHERE o.ename = :ename";
		Query q = emp.get().createQuery(qs);
		q.setParameter("ename", ename);
		return (ObjectClassName) q.getSingleResult();
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	public boolean createOcNames() {
		try {
			ModelAndDao mc = injector.getInstance(ModelAndDao.class);
			Set<String> onames = mc.getDaos().keySet();
			for (String s : onames) {
				if(!obExist(s)){
					ObjectClassName oc = new ObjectClassName();
					oc.setEname(s);
					oc.setCname(s);
					smartPersistBaseModel(oc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean obExist(String ocname) {
		String qs = "SELECT o FROM ObjectClassName AS o WHERE o.ename = :ename";
		Query q = emp.get().createQuery(qs);
		q.setParameter("ename", ocname);
		List<ObjectClassName> ocs = q.getResultList();
		if(ocs.size()>0)return true;
		return false;
	}

	@Override
	public List<ObjectClassName> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

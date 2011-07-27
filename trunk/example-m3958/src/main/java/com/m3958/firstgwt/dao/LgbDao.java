package com.m3958.firstgwt.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.annotation.HowManyMilliSec;
import com.m3958.firstgwt.client.types.GroupByResult;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SortDirection;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.model.LgbSearchCriteria;
import com.m3958.firstgwt.utils.BaseModelUtilsBean;
import com.wideplay.warp.persist.Transactional;


public class LgbDao  extends BaseDao<Lgb>{
	
	@Inject
	BaseModelUtilsBean bmub;
	
	@Inject
	private LgbSearchCriteria lgbc;

	@Inject
	public LgbDao(LgbChangeStrategy task){
		super(Lgb.class);
		extraStrategyTask = task;
	}
	
	@Transactional
	public Lgb changeDepartment(int lgbId,int departmentId){
		Lgb lgb = emp.get().find(Lgb.class, lgbId);
		if(lgb.getDepartment().getId() != departmentId){
			Department dp = emp.get().find(Department.class, departmentId);
			if(dp != null){
				lgb.setDepartment(dp);
			}
		}
		lgb = merge(lgb);
		return lgb;
	}
	
	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	
	@Transactional
	public boolean changeDepartment() {
		try {
			int lgbId = getReqPs().getModelId();
			int departmentId = getReqPs().getIntegerValue(LgbField.DEPARTMENT_ID.getEname());
			Lgb lgb = emp.get().find(Lgb.class, lgbId);
			if(lgb.getDepartment().getId() != departmentId){
				Department dp = emp.get().find(Department.class, departmentId);
				if(dp != null){
					lgb.setDepartment(dp);
				}
			}
			lgb = merge(lgb);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<GroupByResult> getLgbGroupByResults(String groupBy,String did,SortDirection sortdir) {
		String qs = String.format("SELECT new com.m3958.firstgwt.client.types.GroupByResult(l.%s,COUNT(l)) FROM Lgb AS l WHERE l.departmentIds LIKE '%%%s%%' GROUP BY l.%s ORDER by l.%s %s" , groupBy,did,groupBy,groupBy,sortdir.getValue());
		Query q = emp.get().createQuery(qs);
		return q.getResultList();
	}
	
	@HowManyMilliSec
	@SuppressWarnings("unchecked")
	@Override
	public List<Lgb> smartFetch(){
		try {
			bmub.copyProperties(lgbc, getReqPs().getAllParas());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		String cname = this.persistentClass.getSimpleName();
		String whereString = lgbc.getWhereString()+ getShequString() + getOwnerString();
		String qs = "SELECT p FROM " + cname + " p " + paraUtilService.getFinalWhereString(whereString) + getReqPs().getOrderString();
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	@Override
	public Integer smartFetchCount(){
		String cname = this.persistentClass.getSimpleName();
		String whereString = lgbc.getWhereString()+ getShequString() + getOwnerString();
		String qs = "SELECT COUNT(p) FROM " + cname + " P" + paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}
	
	private String getShequString(){
		String shequ = "";
		if(getReqPs().getIdValue(LgbField.SHEQU_ID.getEname()) != SmartConstants.NONE_EXIST_MODEL_ID){
			shequ = " AND p.shequ.id = " + getReqPs().getIntegerValue(LgbField.SHEQU_ID.getEname()) + " ";
		}
		return shequ;
	}
	

	@Override
	public List<Lgb> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}



}

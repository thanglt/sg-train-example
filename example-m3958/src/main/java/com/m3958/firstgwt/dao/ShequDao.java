package com.m3958.firstgwt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.Shequ;


public class ShequDao  extends BaseDao<Shequ>{
	
	@Inject
	public ShequDao(ShequChangeStrategy task) {
		super(Shequ.class);
		extraStrategyTask = task;
	}
	
	/**
	 * 
	 * @param fc
	 * @return
	 * 模式：SELECT l FROM Lgb l WHERE l.relationPropertyName.id = relationObjectId AND l.somefile like '%somevalue%' ORDER BY l.somefield DESC;
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Shequ> smartOneToManyFetch() {
		Department d = getOwnerDepartment();
		if(d == null)return new ArrayList<Shequ>();
		String cname = this.persistentClass.getSimpleName();
		String orderString = getReqPs().getOrderString();
		String relationStr = getRelationString(d);
		String whereString = relationStr + getReqPs().getWhereString();
		
		String qs = "SELECT P FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString) + orderString;
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
		
	}
	
	@Override
	public Integer smartOneToManyCount() {
		Department d = getOwnerDepartment();
		if(d == null)return 0;
		String cname = this.persistentClass.getSimpleName();
		
		String relationStr = getRelationString(d);
		String whereString = relationStr + getReqPs().getWhereString();
		
		String qs = "SELECT COUNT(P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
		
	}
	
	private Department getOwnerDepartment(){
		Department department = emp.get().find(Department.class, getReqPs().getRelationModelId());
		while(department != null){
			 if(department.getShequs().size() > 0){
				 return department;
			 }else{
				 department = department.getParent();
			 }
		}
		return null;
	}
	

	private String getRelationString(Department d) {
		String relationStr = "";		
		relationStr = " P.department.id = " + d.getId();
		return relationStr;
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
	public List<Shequ> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}

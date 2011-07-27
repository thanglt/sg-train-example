package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.PermissionExtraFields;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.SessionUser;

public class PermissionDao  extends BaseDao<Permission>{
	
	@Inject
	public PermissionDao(PermissionChangeStrategy task) {
		super(Permission.class);
		extraStrategyTask = task;
	}
	
	@Inject
	SessionUser su;
	
	@Inject
	SiteConfigService scs;
	
//	@SuppressWarnings("unchecked")
//	public boolean hasClassPermission(String modelName,OpCode operation, int loginUserId){
//		String qs = "SELECT p FROM User AS u , in(u.roles) r,in(r.permissions) p WHERE p.objectClassName = :modelName AND p.classPermission = :classPermission AND p.operation.opCode = :opCode AND u.id = :uid";
//		Query q = emp.get().createQuery(qs);
//		q.setParameter("modelName", modelName);
//		q.setParameter("classPermission", true);
//		q.setParameter("opCode", operation);
//		q.setParameter("uid", loginUserId);
//		List<Permission> ps = q.getResultList();
//		if(ps.size()>0){
//			return true;
//		}else{
//			return false;
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public List<Permission> getClassPermission(String modelName){
		String qs = "SELECT p FROM Permission AS p WHERE p.classPermission = true AND p.objectClassName = :modelName";
		Query q = emp.get().createQuery(qs);
		q.setParameter("modelName", modelName);
		return q.getResultList();
	}
	
	
	

	@Override
	public Integer smartCustomCount() {
		String qs = "SELECT COUNT(p) FROM User AS u,IN(u.roles) r,IN(r.permissions) p WHERE u.id = " + getReqPs().getStringValue(PermissionExtraFields.PERMISSION_OWNER_ID);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();; 
		return l.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseModel> smartCustomFetch() {
		String qs = "SELECT p FROM User AS u,IN(u.roles) r,IN(r.permissions) p WHERE u.id = " + getReqPs().getStringValue(PermissionExtraFields.PERMISSION_OWNER_ID);
		Query q = emp.get().createQuery(qs);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Permission> getAllCp(){
		String qs = "SELECT p FROM Permission AS p WHERE p.classPermission = true";
		Query q = emp.get().createQuery(qs);
		return q.getResultList();
	}	


	@SuppressWarnings("unchecked")
	public List<Permission> getObjectPermissions(int oid){
		String qs = "SELECT p FROM Permission AS p WHERE p.objectIdentity = :oid";
		Query q = emp.get().createQuery(qs);
		q.setParameter("oid", oid);
		return q.getResultList();
	}
	
	@Override
	public List<Permission> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

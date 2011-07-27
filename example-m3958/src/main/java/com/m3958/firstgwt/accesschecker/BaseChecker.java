package com.m3958.firstgwt.accesschecker;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.dao.LgbDao;
import com.m3958.firstgwt.dao.WebSiteDao;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.HasCreator;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.service.ModelAndDao;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.SessionUser;
import com.m3958.firstgwt.utils.BaseModelUtilsBean;

public abstract class  BaseChecker<T extends BaseModel<T>>{
	
	@Inject
	protected ModelAndDao modelConfig;
	
	@Inject
	protected Injector injector;
	
	private T model;
	
	protected T getModel(){
		if(model == null){
			model = getDao().find(reqPs.getModelId());
		}
		return model;
	}
	
	@Inject
	protected SiteConfigService scs;
	
	@Inject
	protected BaseModelUtilsBean bmub;
	
	@Inject
	protected ModelAndDao mc;
	
	@Inject
	protected SessionUser su;
	
	private User loginUser;
	
	protected User getLoginUser(){
		if(loginUser == null){
			loginUser = emp.get().find(User.class, su.getUserId());
		}
		return loginUser;
	}
	
	@Inject
	protected ReqParaService reqPs;
	
	@Inject
	protected RequestScopeObjectService rso;
	
	private BaseDao<T> dao;
	
	@SuppressWarnings("unchecked")
	protected BaseDao<T> getDao(){
		if(dao == null){
			dao = modelConfig.getDaoInstance(reqPs.getModelName());
		}
		return dao;
	}
	
	private SmartOperationName smartOpName;
	
	protected SmartOperationName getOpName(){
		if(smartOpName == null){
			smartOpName = SmartOperationName.NO_OPERATION;
			try {
				smartOpName = SmartOperationName.valueOf(reqPs.getOpType().toUpperCase());
			} catch (Exception e) {}
		}
		return smartOpName;
	}
	
	
	
	private SmartSubOperationName smartSubOpName;
	
	protected SmartSubOperationName getSubOpName(){
		if(smartSubOpName == null){
			smartSubOpName = SmartSubOperationName.NO_SUB_OPERATION;
			try {
				smartSubOpName = SmartSubOperationName.valueOf(reqPs.getSubOpType().toUpperCase());
			} catch (Exception e) {}
		}
		return smartSubOpName;
	}
	

	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	protected abstract boolean canAdd();
	protected abstract boolean canFetch();
	protected abstract boolean canUpdate();
	protected abstract boolean canRemove();
	protected abstract boolean canCustom();
	protected abstract boolean canMyRpc();
	
	public boolean doCheck(){
		if(canAdmin())return true;
		switch (getOpName()) {
		case ADD:
			return canAdd();
		case FETCH:
			return canFetch();
		case UPDATE:
			return canUpdate();
		case REMOVE:
			return canRemove();
		case CUSTOM:
			return canCustom();
		case RPCM_REQ:
			return canMyRpc();
		case ADMIN:
			return canAdmin();
		default:
			break;
		}
		return false;
	}
	
	
	private boolean canAdmin() {
		if(isSuperSupermanLogin()){
			return true;
		}
		return false;
	}
	
	protected boolean isSuperSupermanLogin(){
		return su.isSuperman() && su.getLoginName().equals(SmartConstants.SUPERMAN_USER_NAME);
	}
	
	protected boolean isSuperSuperman(User u){
		if(u.getLoginName().equals(SmartConstants.SUPERMAN_USER_NAME)){
			return true;
		}else{
			return false;
		}
	}

	protected WebSite getSite(int i){
		if(i == SmartConstants.NONE_EXIST_MODEL_ID)return null;
		WebSiteDao wsdao = injector.getInstance(WebSiteDao.class);
		return wsdao.find(i);
	}
	

	
	protected boolean isSiteEditor(int siteId){
		return rso.isSiteEditor(siteId);
	}
	
	protected boolean isSiteOwner(int siteId){
		return rso.isSiteOwner(siteId);
	}
	
	protected boolean isObjectOwner(){
		if(getModel() instanceof HasCreator){
			return ((HasCreator)getModel()).getCreator().getId() == su.getUserId();
		}
		return false;
	}
	
	
	
	protected WebSite getWebSite(){
		int siteId = reqPs.getRelationModelId();
		if(siteId == SmartConstants.NONE_EXIST_MODEL_ID)return null;
		WebSiteDao wsdao = injector.getInstance(WebSiteDao.class);
		return wsdao.find(siteId);
	}

	
	protected Lgb getLgb() {
		LgbDao ldao = injector.getInstance(LgbDao.class);
		int lgbId = reqPs.getRelationModelId();
		Lgb lgb = ldao.find(lgbId);
		return lgb;
	}
	
	protected boolean creatorCheck(String modelName,int oid) {
		HasCreator hc = (HasCreator) mc.getDaoInstance(modelName).find(oid);
		if(hc.getCreator().getId() == su.getUserId())return true;
		return false;
	}
	
	
	protected <S extends BaseModel<S>> boolean isCreator(S obj){
		HasCreator hc = (HasCreator) obj;
		if(hc.getCreator().getId() == su.getUserId())return true;
		return false;
	}
	
	protected int getSiteIdFromPara(){
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		return i;
	}
	

	
	protected boolean hasObjectPerms(String simpleModelName,int oid,ObjectOperation opCode){
		if(isSuperSupermanLogin())return true;
		//用戶在對象上是否有權限
		String qs = "SELECT COUNT(p) FROM " + simpleModelName + " AS o,IN(o.objectPermissions) AS p WHERE p.id IN (" +
				"SELECT p1.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS p1 WHERE u = :u) AND o.id = :oid AND p.opCode = :opCode";
		Query q = emp.get().createQuery(qs);
		q.setParameter("u", getLoginUser());
		q.setParameter("oid", oid);
		q.setParameter("opCode", opCode);
		Long l = (Long) q.getSingleResult();
		if(l >0)return true;
		
		//用戶所屬的組在對象上是否有權限
		qs = "SELECT COUNT(p) FROM " + simpleModelName + " AS o,IN(o.objectPermissions) AS p WHERE p.id IN (" +
		"SELECT p1.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS p1 WHERE u = :u) AND o.id = :oid AND p.opCode = :opCode";
		q = emp.get().createQuery(qs);
		q.setParameter("u", getLoginUser());
		q.setParameter("oid", oid);
		q.setParameter("opCode", opCode);
		l = (Long) q.getSingleResult();
		if(l > 0)return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasTreeContainerPerms(String fullModelName,int oid,ObjectOperation opCode){
		if(isSuperSupermanLogin())return true;
		try {
			@SuppressWarnings("rawtypes")
			Class c = Class.forName(fullModelName);
			TreeModel<T> t = emp.get().find(c,oid);
			while(t != null){
				boolean b = hasObjectPerms(c.getSimpleName(), t.getId(), opCode);
				if(b){
					return true;
				}else{//如果对象具有权限，并且没有对应的权限，就false。也就是對象沒有對象權限的情況下，從上級繼承。
					if(isObjectHasPerms(c.getSimpleName(),t.getId())){
						return false;
					}
					t = (TreeModel<T>) t.getParent();
				}
			}
		} catch (ClassNotFoundException e) {return false;}

		return false;
	}
	
	protected boolean isObjectHasPerms(String simpleModelName,int oid){
		String qs = "SELECT COUNT(p) FROM " + simpleModelName + " AS o,IN(o.objectPermissions) AS p WHERE o.id = :oid";
		Query q = emp.get().createQuery(qs);
		q.setParameter("oid", oid);
		Long l = (Long) q.getSingleResult();
		if(l >0){
			return true;
		}
		return false;
	}
	
}

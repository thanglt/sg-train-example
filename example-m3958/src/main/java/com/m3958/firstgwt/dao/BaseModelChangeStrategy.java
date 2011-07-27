package com.m3958.firstgwt.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;

public abstract class BaseModelChangeStrategy {
	
	@Inject
	protected Injector injector;
	
	private SiteConfigService scs;
	
	protected SiteConfigService getScs(){
		if(scs == null){
			scs = injector.getInstance(SiteConfigService.class);
		}
		return scs;
	}
	
	protected Date afterAddHourMinutes(Date date){
		if(date == null)return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if(c.get(Calendar.HOUR_OF_DAY) == 0 && c.get(Calendar.MINUTE) == 0){
			Calendar cnow = Calendar.getInstance(injector.getInstance(HttpServletRequest.class).getLocale());
			c.set(Calendar.HOUR_OF_DAY, cnow.get(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, cnow.get(Calendar.MINUTE));
		}
		return c.getTime();
	}
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	protected RequestScopeObjectService rso;
	
	protected RequestScopeObjectService getRso(){
		try {
			if(rso == null){
				rso = injector.getInstance(RequestScopeObjectService.class);
			}
			return rso;
		} catch (Exception e) {
			return null;
		}
	}
	
	private ReqParaService reqPs;
	
	protected ReqParaService getReqPs(){
		try {
			if(reqPs == null){
				reqPs = injector.getInstance(ReqParaService.class);
			}
			return reqPs;
		} catch (Exception e) {
			return null;
		}
	}
	
	private SessionUser su;
	
	protected SessionUser getSu(){
		if(su == null){
			su = injector.getInstance(SessionUser.class);
		}
		return su;
	}
	
	private ErrorMessages errors;
	
	protected ErrorMessages getErrors(){
		if(errors == null){
			errors = injector.getInstance(ErrorMessages.class);
		}
		return errors;
	}
	
	protected void createPerRoleAsignToUser(BaseModel bm){
		HasObjectPermission hp = (HasObjectPermission) bm;
		hp.createObjectPermissions();
		hp.createObjectRole(ObjectRoleName.OWNER);
		RoleDao rdao = injector.getInstance(RoleDao.class);
		UserDao udao = injector.getInstance(UserDao.class);
		User cu = udao.find(getSu().getUserId());
		for(Role r:hp.getObjectRoles()){
			rdao.smartPersistBaseModel(r);
			cu.addRole(r);
		}
		udao.merge(cu);
	}
	
	
	protected void addCacheBreakerItem(CacheBreakerItem cacheBreakerItem){
		CacheBreakerItemDao cbidao = injector.getInstance(CacheBreakerItemDao.class);
		cbidao.smartPersistBaseModel(cacheBreakerItem);
		Query q = cbidao.emp.get().createNamedQuery(CacheBreakerItem.NamedQueries.FIND_DUPLICATION);
		q.setParameter("siteId", cacheBreakerItem.getSiteId());
		q.setParameter("obType", cacheBreakerItem.getObType());
		q.setParameter("obId", cacheBreakerItem.getObId());
		q.setParameter("obName", cacheBreakerItem.getObName());
		q.setParameter("action", cacheBreakerItem.getAction());
		q.setParameter("done", false);
		List<CacheBreakerItem> items = q.getResultList();
		for(CacheBreakerItem cbi : items){
			if(cbi.getId() != cacheBreakerItem.getId()){
				cbi.setDone(true);
				cbidao.merge(cbi);
			}
		}
	}
	
}

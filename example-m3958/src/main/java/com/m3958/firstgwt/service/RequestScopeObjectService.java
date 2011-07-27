package com.m3958.firstgwt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.dao.WebHostDao;
import com.m3958.firstgwt.dao.WebSiteDao;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.HasCreator;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.session.SessionUser;


@RequestScoped
public class RequestScopeObjectService {
	
	@Inject
	private SessionUser su;
	
	private User loginUser;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	@Inject
	private Injector injector;
	
	private HttpServletRequest req;
	
	private Map<String, String> uriSegments;
	
	private String remoteIp;
	
	@Inject
	private RequestScopeObjectService(HttpServletRequest req){
		this.req = req;
	}
	
	private WebSite requestWebSite;
	
	private WebHost requestHost;
	
	
	protected User getLoginUser(){
		if(loginUser == null){
			loginUser = emp.get().find(User.class, su.getUserId());
		}
		return loginUser;
	}
	
	public String getRemoteIp(){
		if(remoteIp == null){
			remoteIp = req.getParameter("remoteip");
			if(remoteIp == null || remoteIp.isEmpty()){
				remoteIp = req.getRemoteAddr();
			}
		}
		return remoteIp;
	}
	
	public Map<String, String> getUriSegments(){
		if(uriSegments == null){
			String[] ss = req.getRequestURI().split("/");
			uriSegments = new HashMap<String, String>();
			for(int i = 0 ; i <ss.length;i++){
				if(!ss[i].isEmpty()){
					uriSegments.put("uri" + i, ss[i]);
				}
			}
		}
		return uriSegments;
	}
	
	private WebSite getSiteById(int i){
		if(i == SmartConstants.NONE_EXIST_MODEL_ID)return null;
		WebSiteDao wsdao = injector.getInstance(WebSiteDao.class);
		return wsdao.find(i);
	}

	//一般情况下，一次请求也就是执行一次没有必要缓存。在权限检查和dao里面可能会分别执行一次。
	public boolean isSiteOwner(int siteId){
			WebSite ws = getSiteById(siteId);
			try {
				for(Role r : ws.getObjectRoles()){
					for(Role r1 : getLoginUser().getRoles()){
						if(r1.getId() == r.getId() && r.getOrname() != null && r.getOrname().equals(ObjectRoleName.OWNER.toString())){
							return true;
						}
					}
				}
			} catch (Exception e) {}
		return false;
	}
	
	public boolean isSiteEditor(int siteId){
		WebSite ws = getSiteById(siteId);
		try {
			for(Role r : ws.getObjectRoles()){
				for(Role r1 : getLoginUser().getRoles()){
					if(r1.getId() == r.getId() && r.getOrname().equals(ObjectRoleName.SITE_EDITOR.toString())){
						return true;
					}
				}
			}
		} catch (Exception e) {}
		return false;
	}
	
	
	public boolean isSiteOwner(WebSite ws){
		try {
			for(Role r : ws.getObjectRoles()){
				for(Role r1 : getLoginUser().getRoles()){
					if(r1.getId() == r.getId() && r.getOrname() != null && r.getOrname().equals(ObjectRoleName.OWNER.toString())){
						return true;
					}
				}
			}
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}

	public boolean isSiteEditor(WebSite ws){
		try {
			for(Role r : ws.getObjectRoles()){
				for(Role r1 : getLoginUser().getRoles()){
					if(r1.getId() == r.getId() && r.getOrname().equals(ObjectRoleName.SITE_EDITOR.toString())){
						return true;
					}
				}
			}
		} catch (Exception e) {}
	return false;
	}
	
	public WebSite getRequestWebSite(){
		if(requestWebSite == null){
			WebHost wh = getRequestWebHost();
			if(wh != null){
				requestWebSite =  getRequestWebHost().getWebSite();
			}else{
				return null;
			}
		}
		return requestWebSite;
	}
	
	
	public WebHost getRequestWebHost(){
		if(requestHost == null){
			WebHostDao whd = injector.getInstance(WebHostDao.class);
			requestHost = whd.findByName(getRequestHostName());
		}
		return requestHost;
	}
	
	private String getRequestHostName() {
		String hostName = req.getParameter(AppConstants.HOST_PARAMETER_NAME);
		if(hostName == null || hostName.isEmpty()){
			hostName = req.getRemoteHost();
		}
		return hostName;
	}
	
	public boolean hasObjectRole(HasObjectPermission hp,ObjectRoleName orname){
		Role r = hp.getObjectRole(orname);
		String qs = "SELECT r FROM User AS u,IN(u.roles) r WHERE u.id = :userId AND r = :role";
		Query q = emp.get().createQuery(qs);
		q.setParameter("userId", su.getUserId());
		q.setParameter("role", r);
		List<Role> rs = q.getResultList();
		if(rs != null && rs.size()>0){
			return true;
		}
		return false;
	}
	
	private List<Integer> getModelIds(List<? extends BaseModel> bms){
		List<Integer> iis = new ArrayList<Integer>();
		for(BaseModel bm : bms){
			int i = bm.getId();
			iis.add(i);
		}
		return iis;
	}
	
	public boolean hasObjectRole(int userId,HasObjectPermission hp){
		String qs = "SELECT r FROM User AS u,IN(u.roles) r WHERE u.id = :userId AND r.id IN :roleIds";
		Query q = emp.get().createQuery(qs);
		q.setParameter("userId", userId);
		q.setParameter("roleIds", getModelIds(hp.getObjectRoles()));
		List<Role> rs = q.getResultList();
		if(rs != null && rs.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean isCreator(HasCreator hc){
		if(hc.getCreator().getId() == su.getUserId())return true;
		return false;
	}
	
}

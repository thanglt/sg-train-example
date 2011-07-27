package com.m3958.firstgwt.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.inject.servlet.SessionScoped;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;

@SessionScoped
public class SessionUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String loginName;
	private String email;
	private Set<String> roleNames = new HashSet<String>();
	private boolean superman = false;
	private boolean fcUser = false;
	private String thumbnailUrl;
	
	private Map<String, String> lastFetchUrls = new HashMap<String, String>();
	
	public boolean isFcUser() {
		return fcUser;
	}

	public void setFcUser(boolean fcUser) {
		this.fcUser = fcUser;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public boolean getLoginStatus(){
		if(userId != null || loginName != null){
			return true;
		}
		return false;
	}
	
	public boolean isSuperman() {
		return superman;
	}

	public void setSuperman(boolean superman) {
		this.superman = superman;
	}

	public void setContent(User user){
		userId = user.getId();
		loginName = user.getLoginName();
		email = user.getEmail();
		fcUser = user.getFcUser();
		for(Role r:user.getRoles()){
			if("superman".equals(r.getOrname()))superman = true;
			roleNames.add(r.getOrname());
		}
	}
	
	public void clearContent(){
		userId = null;
		loginName = null;
		email = null;
		roleNames = new HashSet<String>();
		superman = false;
		fcUser = false;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}

	public void setLastFetchUrls(Map<String, String> lastFetchUrls) {
		this.lastFetchUrls = lastFetchUrls;
	}

	public Map<String, String> getLastFetchUrls() {
		return lastFetchUrls;
	}

	public boolean isLogined() {
		return userId != null;
	}
}

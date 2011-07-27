package com.m3958.firstgwt.dao;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.UserField;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.MenuLevel;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.service.MySHAService;


public class UserChangeStrategy  extends BaseModelChangeStrategy implements ModelChangeStrategy<User> {
	
	@Inject
	private MySHAService sha;
	
	
	@Override
	public boolean extraPersistTask(User user) {
		checkPassword(user);
		setInitMenuLevel(user);
		return true;
	}
	
	private void setInitMenuLevel(User user) {
		MenuLevelDao mldao = injector.getInstance(MenuLevelDao.class);
		MenuLevel ml = mldao.findByName(AppConstants.DEFAULT_MENULEVEL);
		user.setMenuLevel(ml);
	}

	private boolean setInitGroup(User user) {
		if(getReqPs() != null){
			Group g = emp.get().find(Group.class, getReqPs().getRelationModelId());
			if( g == null)return true;
			g.getUsers().add(user);
			injector.getInstance(GroupDao.class).update(g);
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean extraUpdateTask(User user,User newModel) {
		checkPassword(newModel);
		MenuLevel ml = emp.get().find(MenuLevel.class, getReqPs().getIdValue(UserField.MENULEVEL_ID.getEname()));
		if(ml != null){
			user.setMenuLevel(ml);
		}
		return true;
	}
	


	@Override
	public boolean extraRemoveTask(User user) {
		return true;
	}
	
	private boolean checkPassword(User model) {
		String p;
		if(getReqPs() != null && getReqPs().getStringValue(UserField.PASSWORD.getEname()) != null){
			p = getReqPs().getStringValue(UserField.PASSWORD.getEname());
		}else{
			p = model.getPassword();
		}
		if(model.getPassword().length() != 28){
			model.setPassword(sha.encrypt(p));
		}
		return true;
	}

	@Override
	public boolean afterPersist(User user) {
		setInitGroup(user);
		return false;
	}
}

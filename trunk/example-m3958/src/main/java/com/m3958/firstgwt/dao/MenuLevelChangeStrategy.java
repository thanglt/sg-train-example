package com.m3958.firstgwt.dao;


import com.m3958.firstgwt.model.MenuLevel;



public class MenuLevelChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<MenuLevel> {

	@Override
	public boolean extraPersistTask(MenuLevel model){
		return true;
	}

	@Override
	public boolean extraUpdateTask(MenuLevel model,MenuLevel newModel){
		return true;
	}
	
	@Override
	public boolean extraRemoveTask(MenuLevel model) {
		return true;
	}

	@Override
	public boolean afterPersist(MenuLevel model) {
		return false;
	}
	
}

	

package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.model.Role;


public class PermissionChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<Permission> {
	



	@Override
	public boolean extraPersistTask(Permission model) {
		return true;
	}
	
	@Override
	public boolean extraUpdateTask(Permission model,Permission newModel) {
		return true;
	}
	
	//刪除permission的時候，必須將持有該permission的role的permissions裡面的東西去掉。
	@Override
	public boolean extraRemoveTask(Permission model) {
		for(Role r:model.getRoles()){
			r.manageRelation(model, false,"", getErrors());
		}
		return true;	
	}


	@Override
	public boolean afterPersist(Permission model) {

		return false;
	}
}

package com.m3958.firstgwt.dao;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.SiteConfigService;

public class GroupChangeStrategy extends TreeChangeStrategy implements ModelChangeStrategy<Group> {
	
	
	@Inject
	protected SiteConfigService scs;
	

	@Override
	public boolean extraPersistTask(Group model){
		return true;
	}

	@Override
	public boolean extraRemoveTask(Group model) {
		if(model == null)return true;
		if(model.getChildren().size() > 0){
			getErrors().addError(new Error("有子目录存在，无法删除",  ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		return true;
	}

	@Override
	public boolean extraUpdateTask(Group model,Group newModel){
		return true;
	}

	@Override
	public boolean afterPersist(Group model) {
		createPerRoleAsignToUser(model);
		return false;
	}

}

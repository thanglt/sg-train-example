package com.m3958.firstgwt.dao;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.SessionUser;

public class AssetFolderChangeStrategy extends TreeChangeStrategy implements ModelChangeStrategy<AssetFolder> {
	
	@Inject
	SessionUser su;
	
	@Inject
	protected SiteConfigService scs;

	@Override
	public boolean extraPersistTask(AssetFolder model){
		if(model.getPerpage() == 0)model.setPerpage(getPerpageFromSite());
		
		return true;
	}
	

	@Override
	public boolean extraRemoveTask(AssetFolder model) {
		
		AssetFolder f = (AssetFolder) model;
		
		if(f.getChildren().size() > 0){
			getErrors().addError(new Error("请先删除子目录！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		
		for(Asset a: model.getAssets()){
			a.getFolders().remove(model);
		}
		return true;
	}
	
	
	@Override
	public boolean extraUpdateTask(AssetFolder model,AssetFolder newModel){
		return true;
	}

	@Override
	public boolean afterPersist(AssetFolder model) {
		createPerRoleAsignToUser(model);
		return false;
	}

}

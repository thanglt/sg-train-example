package com.m3958.firstgwt.dao;

import java.io.File;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.SessionUser;

public class AssetChangeStrategy extends TreeChangeStrategy implements ModelChangeStrategy<Asset> {
	
	@Inject
	SessionUser su;
	
	@Inject
	protected SiteConfigService scs;

	@Override
	public boolean extraPersistTask(Asset model){
		return true;
	}
	

	@Override
	public boolean extraRemoveTask(Asset model) {
		File f = new File(scs.getAssetSavePath(),model.getFilePath());
		if(f.exists())f.delete();
		return true;
	}
	
	
	@Override
	public boolean extraUpdateTask(Asset model,Asset newModel){
		return true;
	}

	@Override
	public boolean afterPersist(Asset model) {
		return false;
	}

}

package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.model.SiteConfig;
import com.m3958.firstgwt.service.SiteConfigService;

public class SiteConfigChangeStrategy extends BaseModelChangeStrategy  implements ModelChangeStrategy<SiteConfig> {


	@Override
	public boolean extraPersistTask(SiteConfig model){
		reloadConfig();
		return true;
	}

	@Override
	public boolean extraRemoveTask(SiteConfig model) {
		reloadConfig();
		return true;
	}

	@Override
	public boolean extraUpdateTask(SiteConfig model,SiteConfig newModel){
		reloadConfig();
		return true;
	}
	
	private boolean reloadConfig(){
		SiteConfigService sc = injector.getInstance(SiteConfigService.class);
		sc.reload();
		return true;
	}

	@Override
	public boolean afterPersist(SiteConfig model) {
		reloadConfig();
		return true;
	}
}

package com.m3958.firstgwt.service;

import java.util.Date;
import java.util.UUID;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.dao.AssetDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.session.ErrorMessages;
import com.wideplay.warp.persist.Transactional;

public abstract class AssetRepositoryImpl {
	
	@Inject
	protected Injector injector;
	
	@Inject
	protected SiteConfigService scs;
	
	@Inject
	protected FilePathService fps;
	
	@Inject
	ErrorMessages errors;
	
	protected Asset createAsset(){
		Asset a = injector.getInstance(Asset.class);
		a.setCreatedAt(new Date());
		a.setFileId(UUID.randomUUID().toString());
		return a;
	}
	
	@Transactional
	protected Asset saveAsset(Asset a) {
		AssetDao dao = injector.getInstance(AssetDao.class);
		try {
			dao.smartPersistBaseModel(a);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}

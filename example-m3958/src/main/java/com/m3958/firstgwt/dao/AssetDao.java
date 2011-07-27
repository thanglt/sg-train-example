package com.m3958.firstgwt.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.service.FilePathService;
import com.m3958.firstgwt.service.SiteConfigService;
import com.wideplay.warp.persist.Transactional;

public class AssetDao  extends BaseDao<Asset>{
	
	@Inject
	private FilePathService fps;
	
	@Inject
	private SiteConfigService scs;
	
	@Inject
	public AssetDao(AssetChangeStrategy task) {
		super(Asset.class);
		this.extraStrategyTask = task;
	}
	
	public boolean assetToDisk(){
		return false;
	}
	
	@Transactional
	@Override
	public List<BaseModel> manageRelation(){
		List<BaseModel> bms = new ArrayList<BaseModel>();
		Asset a = find(getReqPs().getModelId());
		String hint = getReqPs().getStringValue(SmartParameterName.HINT);
		String[] ss = hint.split(",", 2);
		copyToSiteFile(getReqPs().getStringValue(SmartParameterName.EXTRAPARAS), ss[0], ss[1], a);
		bms.add(a);
		smartRemoveBaseModel(a);
		return bms;
	}
	
	private void copyToSiteFile(String siteId, String dirPath, String fname,
			Asset a){
		File f = fps.getFile(siteId, dirPath);
		f = new File(f,fname);
		if(f.exists()){
			f = fps.getNextBakFile(f);
		}
		fps.copyFile(new File(scs.getAssetSavePath(),a.getFilePath()), f);
	}


	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public List<Asset> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}

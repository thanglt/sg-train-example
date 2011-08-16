package com.m3958.firstgwt.client.gin;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.m3958.firstgwt.client.layout.AssetUploadLayout;
import com.m3958.firstgwt.client.service.AssetUploadService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class AssetUploadLayoutProvider implements Provider<AssetUploadLayout>{
	
	private ViewNameEnum sourceViewName = ViewNameEnum.ASSET;
	
	private UploadFor[] uploadFors= new UploadFor[]{UploadFor.UPLOAD_TO_FOLDER};
	
	private Map<UploadFor, String[]> allowExts = new HashMap<UploadFor, String[]>();
	
	@Inject
	private AssetUploadService uploadService;
	
	@Inject
	private VblockService bservice;
	
	public AssetUploadLayoutProvider(){
		allowExts.put(UploadFor.UPLOAD_TO_FOLDER, new String[]{"*"});
	}
	
	@Inject
	private MyEventBus eventBus;

	public AssetUploadLayout get() {
		return new AssetUploadLayout(eventBus,bservice,uploadService,sourceViewName,uploadFors,allowExts);
	}

}

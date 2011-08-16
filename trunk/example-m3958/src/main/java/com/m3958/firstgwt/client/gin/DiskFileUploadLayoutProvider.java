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

public class DiskFileUploadLayoutProvider implements Provider<AssetUploadLayout>{
	
	private ViewNameEnum sourceViewName = ViewNameEnum.DISKFILE;
	
	private UploadFor[] uploadFors= new UploadFor[]{UploadFor.SITE_FILE};
	
	private Map<UploadFor, String[]> allowExts = new HashMap<UploadFor, String[]>();
	
	@Inject
	private AssetUploadService uploadService;
	
	public DiskFileUploadLayoutProvider(){
		allowExts.put(UploadFor.SITE_FILE, new String[]{"*"});
	}
	
	@Inject
	private MyEventBus eventBus;
	
	@Inject
	private VblockService bservice;

	public AssetUploadLayout get() {
		return new AssetUploadLayout(eventBus,bservice,uploadService,sourceViewName,uploadFors,allowExts);
	}

}

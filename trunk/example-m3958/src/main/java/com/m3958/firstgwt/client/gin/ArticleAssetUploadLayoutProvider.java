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

public class ArticleAssetUploadLayoutProvider implements Provider<AssetUploadLayout>{
	
	private ViewNameEnum sourceViewName = ViewNameEnum.ARTICLE_EDIT;
	
	private UploadFor[] uploadFors= new UploadFor[]{UploadFor.TITLE_IMG,UploadFor.CONTENT_IMG,UploadFor.ATTACHMENT};
	
	private Map<UploadFor, String[]> allowExts = new HashMap<UploadFor, String[]>();
	
	@Inject
	private AssetUploadService uploadService;
	
	@Inject
	private VblockService bservice;
	
	public ArticleAssetUploadLayoutProvider(){
		allowExts.put(UploadFor.TITLE_IMG, new String[]{"jpg","gif","bmp","png","jpeg"});
		allowExts.put(UploadFor.CONTENT_IMG, new String[]{"jpg","gif","bmp","png","jpeg"});
		allowExts.put(UploadFor.ATTACHMENT, new String[]{"*"});
	}
	
	@Inject
	private MyEventBus eventBus;

	@Override
	public AssetUploadLayout get() {
		return new AssetUploadLayout(eventBus,bservice,uploadService,sourceViewName,uploadFors,allowExts);
	}

}

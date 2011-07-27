package com.m3958.firstgwt.client.layout;


import java.util.Map;

import com.google.inject.Provider;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.service.AssetUploadService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AssetUploadLayout extends VLayout{
	
	private VLayout uploadLayout = new VLayout();
	
	private ViewNameEnum sourceViewName;
	
	private AssetUploadService uploadService;
	
	private VblockService bservice;
	
	private UploadFor[] uploadFors;
	
	private MyEventBus eventBus;
	
	private Map<UploadFor, String[]> allowExts;

	
	public AssetUploadLayout(MyEventBus eventBus,VblockService bservice,AssetUploadService uploadService,ViewNameEnum sourceViewName,UploadFor[] uploadFors,Map<UploadFor, String[]> allowExts){
		this.sourceViewName = sourceViewName;
		this.uploadFors = uploadFors;
		this.eventBus = eventBus;
		this.allowExts = allowExts;
		this.uploadService = uploadService;
		this.bservice = bservice;
		addMember(initUploadBt());
		addMember(uploadLayout);
	}
	
	protected  Provider<AssetUploadForm> getProVider(){
		return null;
	};
	
	private Canvas initUploadBt() {
		HLayout lay = new HLayout(5);
		IButton uploadButton = new IButton("上传附件");
		uploadButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				AssetUploadForm auf = new AssetUploadForm(eventBus,bservice,uploadService,sourceViewName, uploadFors,allowExts);
				addMember(auf);
			}});
		
		lay.addMember(uploadButton);
		
		return lay;
	}
}

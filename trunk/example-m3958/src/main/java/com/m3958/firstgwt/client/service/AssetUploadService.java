package com.m3958.firstgwt.client.service;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.event.AfterFileUploadEvent;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.jso.UploadResponseJso;
import com.m3958.firstgwt.client.types.UploadResponseStatus;
import com.smartgwt.client.util.SC;

@Singleton
public class AssetUploadService {
	
	private MyEventBus eventBus;
	
	
	private AppUiService auiService;
	
	@Inject
	public AssetUploadService(MyEventBus eventBus,AppUiService auiService){
		this.eventBus = eventBus;
		this.auiService = auiService;
		exportStaticMethod();
	}
	
	public final native void exportStaticMethod()/*-{
		var _this = this;
		$wnd.assetsMethods = {
			afterUpload: function(resStr){
				_this.@com.m3958.firstgwt.client.service.AssetUploadService::afterUpload(Ljava/lang/String;)(resStr);
			}
		};
	}-*/;
	
	public void afterUpload(String resStr){
		UploadResponseJso urjso = (UploadResponseJso)JSONParser.parseStrict(resStr).isObject().getJavaScriptObject();
		if(urjso.getStatus() != UploadResponseStatus.SUCCESS){
			auiService.showTmpPrompt(urjso.getErrorMsg(), 1000);
		}
		eventBus.fireEvent(new AfterFileUploadEvent(urjso));
	}

	
	@SuppressWarnings("unchecked")
	public AssetJso getFirstAsset(UploadResponseJso urj){
		JsArray<AssetJso> assets = (JsArray<AssetJso>)urj.getAssets();
		if(assets.length()>0)return assets.get(0);
		return null;
	}
}

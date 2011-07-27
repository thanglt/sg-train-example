package com.m3958.firstgwt.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.service.AssetUploadService;
import com.m3958.firstgwt.client.service.GwtRPCServiceAsync;
import com.m3958.firstgwt.client.service.RequestAndConfirmService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.slots.GlobalSlot;
import com.m3958.firstgwt.client.slots.Slot;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.NativeMethods;
import com.m3958.firstgwt.client.window.MessageWindow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;

@Singleton
public class App {
	
	
	private AppSlot appSlot;
	
	private AppStatusService aservice;
	
	private VblockService bservice;
	
	@Inject
	private GwtRPCServiceAsync gwtRPCService;
	
	@Inject
	private MessageWindow mw;
	
	private Slot slot;
	
	@Inject
	public App(AppSlot appSlot,AppStatusService aservice,VblockService bservice,AssetUploadService aus,RequestAndConfirmService rcs,GlobalSlot gslot,Slot slot){
		this.slot = slot;
		this.appSlot = appSlot;
		this.aservice  = aservice;
		this.bservice = bservice;
		aus.exportStaticMethod();
		slot.exportStaticMethod();
		DateUtil.setDefaultDisplayTimezone("00:00");
	}
	
	public void start(){
		
		mw.show();
//		mw.animateMove(left, top);
		String tz = NativeMethods.getTimeZone();
		if(tz != null){
			bservice.setTimeZone(tz);
		}else{
			bservice.setTimeZone("GMT+08:00");
		}
		
	    final ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {
	    	public void onValueChange(ValueChangeEvent<String> event) {
	    		String token = event.getValue();
	    		bservice.buildBlocks(token);
	    	}
	    };
	    History.addValueChangeHandler(historyHandler);
	    
		gwtRPCService.loginStatus(new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("应用程序出错！");
			}
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					aservice.start();
				}else{
					bservice.b.setValues(ViewNameEnum.LOGIN, ViewAction.NO_ACTION);
					bservice.addHistoryItemAndFireApplicationEvent();
				}
			}});
		if(History.getToken().contains("/LOGIN/") || History.getToken().startsWith("!")){
			aservice.setInitToken("");
		}else{
			aservice.setInitToken(History.getToken());
		}
	}
	
	public AppSlot getAppSlot(){
		return appSlot;
	}
}

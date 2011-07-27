package com.m3958.firstgwt.client.datasource;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.event.LoginEvent;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartResponseFieldName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.util.SC;

public class DsErrorHandler implements HandleErrorHandler {
	
	@Inject
	private AppStatusService aservice;
	
	@Inject
	private VblockService bservice;
	

	@Inject
	private MyEventBus eventBus;
	
	public void onHandleError(ErrorEvent event) {
		DSResponse response = event.getResponse();
		int code = 0;
		Record r = null; 
		if(response.getData().length > 0){
			r = response.getData()[0];
			code = r.getAttributeAsInt(SmartResponseFieldName.ERROR_CODE);
			
		}
		if(code != 0){
			if(code == ServerErrorCode.LOGIN_REQUIRED){
				aservice.setLogined(false);
				if(!History.getToken().contains("/LOGIN/")){
					aservice.setInitToken(History.getToken());
					eventBus.fireEvent(new LoginEvent(false));
					bservice.clearAllBlockContents();
					bservice.b.setValues(ViewNameEnum.LOGIN, ViewAction.NO_ACTION);
					bservice.addHistoryItemAndFireApplicationEvent();
				}
			}else{
				String es = r.getAttributeAsString(SmartResponseFieldName.ERROR_MESSAGE);
				if(es.contains("Duplicate entry")){
					SC.warn("重复的记录！");
				}else{
					SC.warn(es);
				}
			}
		}
		
	}

}

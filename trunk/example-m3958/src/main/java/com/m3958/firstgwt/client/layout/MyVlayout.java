package com.m3958.firstgwt.client.layout;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.FirstGwtConstants;
import com.m3958.firstgwt.client.SelectedRecordService;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.CommonField;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MyVlayout extends VLayout{
	
	protected FirstGwtConstants constants = GWT.create(FirstGwtConstants.class);
	
	@Inject
	protected AppStatusService aservice;
	
	@Inject
	protected AppUtilService auService;
	
	@Inject
	protected SelectedRecordService srservice;
	
	@Inject
	protected AppUiService auiService;
	
	protected boolean initialized = false;
	
	protected boolean firstCall = false;
	
	@Inject
	protected VblockService bservice;
	
	protected TextItem getPositionItem(boolean toTop){
	    TextItem positionItem = new TextItem(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
	    if(toTop)
	    	positionItem.setTitleOrientation(TitleOrientation.TOP);
	    return positionItem;
	}
	
	protected TextItem getTplNameItem(boolean toTop){
	    TextItem tplNameItem = new TextItem(CommonField.TPL_NAME.getEname(),CommonField.TPL_NAME.getCname());
	    if(toTop)
	    	tplNameItem.setTitleOrientation(TitleOrientation.TOP);
	    return tplNameItem;
	}
	
	@Inject
	protected MyEventBus eventBus;
	
	public Layout asLayout() {
		return this;
	}

}

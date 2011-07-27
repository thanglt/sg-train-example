package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.FirstGwtConstants;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.ObjectClassNameDataSource;
import com.m3958.firstgwt.client.datasource.PermissionDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.AdminSubOperation;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class ToolsLayout extends MyVlayout implements Iview{
	
	final FirstGwtConstants constants = GWT.create(FirstGwtConstants.class);
	
	DynamicForm form;

	private Canvas initAdminForm() {
		VLayout l = new VLayout(5);
		form = new DynamicForm();
		form.setWidth(450);
		form.setNumCols(4);
		TextItem ti = new TextItem("actionParas","参数(可选)");
		SelectItem si = new SelectItem("adminAction","任务");
		AdminSubOperation[] ops = AdminSubOperation.values();
		LinkedHashMap<String, String> opss = new LinkedHashMap<String, String>();
		for(AdminSubOperation aso : ops){
			opss.put(aso.toString(), aso.getValue());
		}
		si.setValueMap(opss);
		si.setDefaultToFirstOption(true);
		
		form.setFields(ti,si);
		form.setCanSubmit(false);
		l.addMember(form);
		IButton bt = new IButton("执行");
		l.addMember(bt);
		
		bt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.confirm("确认执行？",new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if(value)takeAction();
						
					}
				});
			}
		});
		return l;
	}

	private void takeAction(){
		String ac = form.getValueAsString("adminAction");
		String actionParas = form.getValueAsString("actionParas");
		AdminSubOperation aso = AdminSubOperation.valueOf(ac);
		switch (aso) {
		case FIX_PARENT_ID:
			sendRequest(aso,actionParas, ObjectClassNameDataSource.className);
			break;
		case CREATE_OC:
			sendRequest(aso,actionParas, PermissionDataSource.className);
			break;
		default:
			sendRequest(aso, actionParas, PermissionDataSource.className);
			break;
		}
	}
	
	private void sendRequest(AdminSubOperation aso,String actionParas,String modelName){
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.ADMIN.getValue());
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, aso.toString());
    	m.put(SmartParameterName.MODEL_NAME, modelName);
    	m.put(SmartParameterName.EXTRAPARAS, actionParas);
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("POST");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	
    	RPCManager.sendRequest(rr,new RPCCallback(){
			@Override
			public void execute(RPCResponse response, Object rawData,
					RPCRequest request) {
				SC.say(rawData.toString());
			}});			
	}


	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			addMember(initAdminForm());
			initialized = true;
		}
	}


	@Override
	public String[] getParas() {
		return null;
	}


	@Override
	public Btname[] getStripStatus() {
		return null;
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.TOOLS;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	public void setUnInitialized() {
		
	}

}

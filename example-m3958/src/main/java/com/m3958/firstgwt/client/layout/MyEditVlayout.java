package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.m3958.firstgwt.client.FirstGwtConstants;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;


public abstract class MyEditVlayout extends MyVlayout implements Iview{
	
	protected enum BtHandlerType{
		SIMPLE,WITH_RELATION,TREE
	}
	
	protected FirstGwtConstants constants = GWT.create(FirstGwtConstants.class);
	
	protected DynamicForm modelForm;
	
	protected IButton button = new IButton();
	
	protected abstract String getModelName();
	
	protected abstract BtHandlerType getBtHandlerType();
	
	protected String relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	protected String parentId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	protected String editRecordId;
	
	public MyEditVlayout(){
		setMargin(3);
		setBorder("1px solid grey");
		setMembersMargin(5);
	}
	
	private DSRequest createDsRequest(){
		DSRequest dr = new DSRequest();
		Map<String, String> m = new HashMap<String, String>();
		m.put(SmartParameterName.RELATION_MODEL_ID, relationModelId);
		m.put(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		dr.setParams(m);
		return dr;
	}

	private DSRequest createTreeDsRequest(){
		DSRequest dr = new DSRequest();
		Map<String, String> m = new HashMap<String, String>();
		m.put(SmartParameterName.PARENTID, parentId);
		dr.setParams(m);
		return dr;
	}

	
	protected void editModel(Record r){
		modelForm.editRecord(r);
		button.setTitle(constants.cwFormSaveButtonLabel());
	}
	
	protected void newModel(){
		modelForm.editNewRecord();
		button.setTitle(constants.cwFormCreateButtonLabel());
	}
	
	private void simpleChangeDisplay(ViewAction va,String... paras){
		if(!initialized){
			initLayout();
			initialized = true;
		}
//		addMember(auiService.getBackBt(),0);
		switch (va) {
		case EDIT:
			Record r = srservice.getRecord(auService.getStringIdPara(paras,0)); 
			if(r != null){
				editModel(r);
			}else{
				RPCManager.sendRequest(auService.getFetchOneRpcRequest(auService.getStringIdPara(paras,0), getModelName()), new MyRpcCallback() {
					@Override
					public void afterSuccess(RPCResponse response, Object rawData,
							RPCRequest request, JSONValue data) {
						JSONArray ja = data.isArray();
						if(ja.size() == 1){
							Record r = new Record(ja.get(0).isObject().getJavaScriptObject());
							editModel(r);
						}else{
							SC.say("编辑对象不存在，可能已经删除！");
						}
					}
				});
			}			
			break;
		case ADD:
			newModel();
			break;
		default:
			break;
		}
	}
	
	private void relationChangeDisplay(ViewAction va,String... paras){
		if(!initialized){
			initLayout();
			initialized = true;
		}
//		addMember(auiService.getBackBt(),0);
		relationModelId = auService.getStringIdPara(paras, 0);
		editRecordId = auService.getStringIdPara(paras, 1);
		switch (va) {
		case EDIT:
			Record r = srservice.getRecord(editRecordId); 
			if(r != null){
				editModel(r);
			}else{
				RPCManager.sendRequest(auService.getFetchOneRpcRequest(editRecordId, getModelName()), new MyRpcCallback() {
					@Override
					public void afterSuccess(RPCResponse response, Object rawData,
							RPCRequest request, JSONValue data) {
						JSONArray ja = data.isArray();
						if(ja.size() == 1){
							Record r = new Record(ja.get(0).isObject().getJavaScriptObject());
							editModel(r);
						}else{
							SC.say("编辑对象不存在，可能已经删除！");
						}
					}
				});
			}
			break;
		case ADD:
			newModel();
			break;
		default:
			break;
		}
	}
	
	private void treeChangeDisplay(ViewAction va,String... paras){
		if(!initialized){
			initLayout();
			initialized = true;
		}
//		addMember(auiService.getBackBt(),0);
		relationModelId = auService.getStringIdPara(paras, 0);
		editRecordId = auService.getStringIdPara(paras, 1);
		switch (va) {
		case EDIT:
			Record r = srservice.getRecord(editRecordId); 
			if(r != null){
				editModel(r);
			}else{
				RPCManager.sendRequest(auService.getFetchOneRpcRequest(editRecordId, getModelName()), new MyRpcCallback() {
					@Override
					public void afterSuccess(RPCResponse response, Object rawData,
							RPCRequest request, JSONValue data) {
						JSONArray ja = data.isArray();
						if(ja.size() == 1){
							Record r = new Record(ja.get(0).isObject().getJavaScriptObject());
							editModel(r);
						}else{
							SC.say("编辑对象不存在，可能已经删除！");
						}
					}
				});
			}
			break;
		case ADD:
			parentId = auService.getStringIdPara(paras, 0);
			newModel();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		switch (getBtHandlerType()) {
		case SIMPLE:
			simpleChangeDisplay(va, paras);
			break;
		case WITH_RELATION:
			relationChangeDisplay(va, paras);
			break;
		case TREE:
			treeChangeDisplay(va,paras);
			break;
		default:
			break;
		}
	}
	
	protected abstract void initModelForm();
	
	private ClickHandler btSimpleHandler = new ClickHandler() {
        public void onClick(ClickEvent event) {
        	if(modelForm.validate(false)){
        		modelForm.saveData(new MyDsCallback(){
					@Override
					public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
						if(constants.cwFormCreateButtonLabel().equals(button.getTitle())){
							newModel();
						}else{
							bservice.back();
						}
					}});
        	}
        }
     };
     
     private ClickHandler btRelationHandler = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(modelForm.validate(false)){
	        		modelForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
							if(constants.cwFormCreateButtonLabel().equals(button.getTitle())){
								newModel();
							}else{
								bservice.back();
							}
						}},createDsRequest());
	        	}
	        }
	        
	    };
	    
	     private ClickHandler btTreeHandler = new ClickHandler() {
		        public void onClick(ClickEvent event) {
		        	if(modelForm.validate(false)){
		        		modelForm.saveData(new MyDsCallback(){
							@Override
							public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
								if(constants.cwFormCreateButtonLabel().equals(button.getTitle())){
									newModel();
								}else{
									bservice.back();
								}
							}},createTreeDsRequest());
		        	}
		        }
		    };
	
	private void addListener(){
		switch (getBtHandlerType()) {
		case SIMPLE:
			button.addClickHandler(btSimpleHandler);
			break;
		case WITH_RELATION:
			button.addClickHandler(btRelationHandler);
		case TREE:
			button.addClickHandler(btTreeHandler);
		default:
			break;
		}
	    
	}
	
	protected abstract void initOtherWidget();
	
	protected void initLayout(){
		initModelForm();
		addMember(modelForm);
		addMember(button);
		initOtherWidget();
		addListener();
	}
}

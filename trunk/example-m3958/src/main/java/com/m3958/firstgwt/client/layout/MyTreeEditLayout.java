package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.m3958.firstgwt.client.FirstGwtConstants;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
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

public abstract class MyTreeEditLayout  extends MyVlayout implements Iview{

	protected FirstGwtConstants constants = GWT.create(FirstGwtConstants.class);
	
	protected DynamicForm modelForm;
	
	protected IButton button = new IButton();
	
	protected abstract String getModelName();

	protected String parentId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	protected String editRecordId;
	
	protected Record editingRecord;
	
	protected boolean editing;
	
	public MyTreeEditLayout(){
		setMargin(3);
		setBorder("1px solid grey");
		setMembersMargin(5);
	}
	
	protected DSRequest createTreeDsRequest(){
		DSRequest dr = new DSRequest();
		Map<String, String> m = new HashMap<String, String>();
		m.put(SmartParameterName.PARENTID, parentId);
		m.put(CommonField.SITE_ID.getEname(), bservice.getSiteId());
		m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
		dr.setParams(m);
		return dr;
	}

	
	protected void editModel(Record r){
		editingRecord = r;
		editing = true;
		modelForm.editRecord(r);
		button.setTitle(constants.cwFormSaveButtonLabel());
	}
	
	protected void newModel(){
		editing = false;
		modelForm.editNewRecord();
		button.setTitle(constants.cwFormCreateButtonLabel());
	}
	
	@Override
	public void setUnInitialized() {}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initLayout();
			initialized = true;
		}
		switch (va) {
		case EDIT:
			editRecordId = auService.getStringIdPara(paras,0);
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
			editing = true;
			break;
		case ADD:
			parentId = auService.getStringIdPara(paras,0);
			newModel();
			editing = false;
			break;
		default:
			parentId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
			break;
		}
	}
	
	protected abstract void initModelForm();
    
     protected ClickHandler getBtTreeHandler(){
	     return new ClickHandler() {
		        public void onClick(ClickEvent event) {
		        	if(modelForm.validate(false)){
		        		if(editing){
			        		modelForm.saveData(new MyDsCallback(){
								@Override
								public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
									bservice.back();
								}});
		        		}else{
			        		modelForm.saveData(new MyDsCallback(){
								@Override
								public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
									bservice.back();
								}},createTreeDsRequest());
		        		}
		        	}
		        }
		    };
     }
	
	private void addListener(){
		button.addClickHandler(getBtTreeHandler());
	}
	
	protected abstract void initOtherWidget();
	
	protected void initLayout(){
		initModelForm();
		addMember(modelForm);
		addMember(button);
		initOtherWidget();
		addListener();
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
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}
}

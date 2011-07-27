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
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class HasMMRelationEditLayout extends MyVlayout implements Iview{
	
	protected FirstGwtConstants constants = GWT.create(FirstGwtConstants.class);
	protected DynamicForm modelForm;
	protected IButton button;
	protected abstract String getModelName();
	
	protected boolean edit;
	

	//在编辑的时候，就是RecordID
	protected String relationModelId;

	protected String editRecordId;
	
	public HasMMRelationEditLayout(){
		setMargin(3);
		setBorder("1px solid grey");
		setMembersMargin(5);
	}
	
	protected DSRequest createDsRequest(){
		DSRequest dr = new DSRequest();
		Map<String, String> m = new HashMap<String, String>();
		m.put(SmartParameterName.RELATION_MODEL_ID, relationModelId);
		if(!isEdit()){
			m.put(CommonField.SITE_ID.getEname(), bservice.getSiteId());
		}
		m.put(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
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
	
	@Override
	public void setUnInitialized() {}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initLayout();
			initialized = true;
		}
		relationModelId = auService.getStringIdPara(paras, 0);
		switch (va) {
		case EDIT:
			setEdit(true);
			editRecordId = auService.getStringIdPara(paras, 1);
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
			editRecordId = null;
			setEdit(false);
			newModel();
			break;
		default:
			break;
		}
	}
	
	@Override
	public Btname[] getStripStatus() {
		return null;
	}
	
	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	public String[] getParas() {
		return null;
	}
	
	protected abstract void initModelForm();
	
    protected ClickHandler getCreateSaveBtHandler(){
    	ClickHandler bth = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(modelForm.validate(false)){
	        		modelForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
								bservice.back();
						}},createDsRequest());
	        	}
	        }
	    };
	    return bth;
    }
	    
	
	private void addListener(){
		button.addClickHandler(getCreateSaveBtHandler());
	}
	
	protected  void initOtherWidget(){
		
	}
	
	protected void initLayout(){
		initModelForm();
		addMember(modelForm);
		button = new IButton();
		addMember(button);
		VLayout l = new VLayout();
		l.setHeight(30);
		addMember(l);
		initOtherWidget();
		addListener();
	}
	
	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}
}

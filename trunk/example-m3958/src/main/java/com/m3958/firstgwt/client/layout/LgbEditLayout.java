package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.event.LgbRelationEventHandler;
import com.m3958.firstgwt.client.event.LgbRelationGridEvent;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.jso.LgbJso;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.IHasAttachment;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;


@Singleton
public class LgbEditLayout extends HasMMRelationEditLayout implements Iview,IHasAttachment{
	
	@Inject
	private LgbForm lgbForm;
	
	@Inject
	private LgbRelationLayout lgbGrids;
	
	public Btname[] simpleBts0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] simpleBts1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH};
	public Btname[] simpleBts2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	
	private IButton button = new IButton(constants.cwFormCreateButtonLabel()); 
	
	private Record lgb;
	
	private Btname[] btnames;
	
	@Inject @Named("LGB_EDIT")
	private AssetUploadLayout aul;
	
	private String selectedGridRecordId;
	
	private ViewNameEnum nextView;
	
	@Inject
	private LgbTitleImgBox titleImgBox;
	
	@Inject
	private LgbAttachmentBox attachmentBox;
	
	
	@Inject
	public LgbEditLayout(final MyEventBus eventBus){
		this.eventBus = eventBus;
		setWidth100();
		setHeight100();
		setOverflow(Overflow.AUTO);
		eventBus.addHandler(LgbRelationGridEvent.TYPE, new LgbRelationEventHandler() {
			@Override
			public void onLgbGridEvent(LgbRelationGridEvent event) {
				if(event.getEvType() == LgbRelationGridEvent.EvType.ACTIVE){
					btnames = simpleBts0;
				}else{
					if(event.getParas() == null || event.getParas().length == 0){
						btnames = simpleBts0;
						selectedGridRecordId = null;
					}else if(event.getParas().length == 1){
						btnames = simpleBts1;
						selectedGridRecordId = event.getParas()[0];
					}else{
						btnames = simpleBts2;
					}
				}
				nextView = event.getNextView();
				RecordEvent re = new RecordEvent(getViewName(),RecordEventType.SELECT);
				eventBus.fireEvent(re);
			}
		});
	}
	
	private void initContent() {
		addMember(lgbForm);
		addMember(initBts());
		addMember(aul);
		addMember(titleImgBox);
		addMember(attachmentBox);
		addMember(lgbGrids);
	}
	
	private HLayout initBts(){
		HLayout layout = new HLayout(10);
	    button.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(lgbForm.validate(false)){
	        		lgbForm.setValue(LgbField.DEPARTMENT_ID.getEname(), relationModelId);
	        		lgbForm.setValue(SmartParameterName.ATTACHMENT_JSON, getAttachmentJsonStr());
	        		lgbForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
								bservice.back();
						}},createDsRequest());
	        	}
	        }
	    });
	    
	    layout.addMember(button);
		return layout;
	}
	
    private String getAttachmentJsonStr(){
    	JSONArray ja = new JSONArray();
    	ja.set(0, titleImgBox.getJsonObject());
    	ja.set(1, attachmentBox.getJsonObject());
    	return ja.toString();
    }
    

	@Override
	public void editModel(Record r){
		lgbForm.editRecord(r);
        Object shequIdo = r.getAttributeAsInt(LgbField.SHEQU_ID.getEname());
        if(shequIdo != null && !SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(shequIdo.toString())){
        	lgbForm.setValue(LgbField.SHEQU_ID.getEname(), shequIdo.toString());
        }
		button.setTitle(constants.cwFormSaveButtonLabel());
		
		LgbJso ajso = (LgbJso) r.getJsObj();
		titleImgBox.setCattachment(ajso.getZp());
		attachmentBox.setCattachments(ajso.getAttachments());
	}
	
	@Override
	public void newModel(){
		if(!aservice.isConfirmInProgress()){
			lgbForm.editNewRecord();
			lgbGrids.fetchData(SmartConstants.NONE_EXIST_MODEL_ID_STR);
			button.setTitle(constants.cwFormCreateButtonLabel());
			clearCattachments();
		}
		aservice.setConfirmInProgress(false);
	}
	
	private void clearCattachments(){
		titleImgBox.clearCattachments();
		attachmentBox.clearCattachments();
	}

	//departmnetId是始终需要的，所以这是特别的地方。
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initContent();
			initialized = true;
		}
		relationModelId = auService.getStringIdPara(paras, 0);
		switch (va) {
		case EDIT:
			setEdit(true);
			if(!auService.getStringIdPara(paras,1).equals(editRecordId)){
				editRecordId = auService.getStringIdPara(paras,1);
				lgbGrids.fetchData(editRecordId);
			}
			RPCManager.sendRequest(auService.getFetchOneRpcRequest(editRecordId, LgbDataSource.className), new MyRpcCallback() {
				@Override
				public void afterSuccess(RPCResponse response, Object rawData,
						RPCRequest request, JSONValue data) {
					JSONArray ja = data.isArray();
					if(ja.size() == 1){
						lgb = new Record(ja.get(0).isObject().getJavaScriptObject());
						editModel(lgb);
					}else{
						SC.say("编辑对象不存在，可能已经删除！");
					}
				}
			});
			lgbGrids.disableAll();
			break;
		case ADD:
			lgbForm.setDepartmentId(relationModelId);
			newModel();
			setEdit(false);
			lgbGrids.disableAll();
			break;
		case REMOVE:
			lgbGrids.removeSelected(nextView);//当前的nextview决定删除那个grid的数据
			break;
		default:
			break;
		}
		
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.LGB_EDIT;
	}


	@Override
	public Btname[] getStripStatus() {
		return btnames;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return nextView;
	}

	@Override
	public String[] getParas() {
		return new String[]{editRecordId,selectedGridRecordId};
	}


	@Override
	protected void initModelForm() {
	}

	@Override
	protected void initOtherWidget() {
		
	}

	@Override
	public String getModelName() {
		return LgbDataSource.className;
	}

	@Override
	public void addAttachment(AssetJso assetJso, UploadFor uploadFor) {
		switch (uploadFor) {
		case LGB_ZP:
			titleImgBox.addCattachment(assetJso);
			break;
		default:
			attachmentBox.addCattachment(assetJso);
			break;
		}
		
	}
}

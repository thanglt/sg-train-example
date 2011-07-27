package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.SectionDataSource;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SectionField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

@Singleton
public class SectionEditLayout extends MyTreeEditLayout implements Iview{
	

	@Inject
	private SectionDataSource sds;
	

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(sds);
	    
	    TextItem nameItem = new TextItem(CommonField.NAME.getEname(),CommonField.NAME.getCname());
	    nameItem.setRequired(true);
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);

	    CheckboxItem ishiddenItem = new CheckboxItem(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
	    
	    CheckboxItem sectionContainerItem = new CheckboxItem(SectionField.SECTION_CONTAINER.getEname(),SectionField.SECTION_CONTAINER.getCname());

	    TextAreaItem kvItem = new TextAreaItem(CommonField.KEY_VALUES.getEname(),CommonField.KEY_VALUES.getCname());
	    kvItem.setTitleOrientation(TitleOrientation.TOP);
	    kvItem.setColSpan(2);
	    kvItem.setHeight(100);
	    LengthRangeValidator lrv = new LengthRangeValidator();
	    lrv.setMin(2);
	    lrv.setMax(40);
	    nameItem.setValidators(lrv);
	    modelForm.setFields(auiService.getIdDisableItem(false),nameItem,parentIdItem,auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),getTplNameItem(false),getPositionItem(false),auiService.getProtectLevelItem(),ishiddenItem,auiService.getPerpageItem(false),kvItem,sectionContainerItem);
	}
	
	@Override
	protected void editModel(Record r){
		editing = true;
		modelForm.editRecord(r);
		button.setTitle(constants.cwFormSaveButtonLabel());
	}
	
	@Override
	protected void newModel(){
		editing = false;
		modelForm.editNewRecord();
		button.setTitle(constants.cwFormCreateButtonLabel());
	}
	
	
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
//			importBt.show();
			break;
		case ADD:
			parentId = auService.getStringIdPara(paras,0);
			newModel();
			editing = false;
//			importBt.hide();
			break;
		default:
			parentId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
			break;
		}
	}

	@Override
	protected String getModelName() {
		return SectionDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
//		iaw = new ImportArticleWindow();
//		importBt = new IButton("导入文章", new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				iaw.show();
//				iaw.addUserClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
//					
//					@Override
//					public void onClick(
//							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
//						if("cancelbt".equals(event.getItem().getName())){
//							iaw.hide();
//							return;
//						}
//						
//						if(!iaw.validate()){
//							SC.say("所有字段都必须填写！");
//							return;
//						}
//						
//				    	Map<String, String> m = new HashMap<String, String>();
//				    	m.put(SmartParameterName.MODEL_NAME, WebSiteDataSource.className);
//				    	m.put(SmartParameterName.OPERATION_TYPE,SmartOperationName.CUSTOM.getValue());
//				    	m.put(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.MQ.toString());
//				    	m.put("dowhat","importArticle");
//				    	m.putAll(iaw.getValueMap());
//				    	m.put("sectionId", editRecordId+"");
//				    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
//				    	RPCRequest rr = new RPCRequest();
//				    	rr.setHttpMethod("GET");
//				    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
//				    	rr.setParams(m);
//				    	RPCManager.sendRequest(rr,new MyRpcCallback() {
//							@Override
//							public void afterSuccess(RPCResponse response, Object rawData,
//									RPCRequest request, JSONValue data) {
//								if(data.isBoolean().booleanValue()){
//									SC.say("任务已经提交，完成之后系统会通知您！当然您也可以按刷新来查看！");
//								}else{
//									SC.say("不好意思，任务无法完成！");
//								}
//							}
//						});
//						iaw.hide();
//					}
//				});
//				
//			}
//		});
//		addMember(importBt);
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.SECTION_EDIT;
	}

}

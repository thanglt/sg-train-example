package com.m3958.firstgwt.client.layout;

import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.DiskFileDataSource;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

@Singleton
public class DiskFileEditLayout extends HasMMRelationEditLayout implements Iview{
	
	
	private String siteId;
	private String dirPath;
	private String fileName;
	private String oldFileName;
	private boolean isFolder;
	
	private CheckboxItem fItem;
	private TextAreaItem contentItem;
	

	@Inject
	private DiskFileDataSource dfds;
	
	@Override
	protected void editModel(Record r){
		modelForm.editRecord(r);
		if(isFolder || !aservice.allowEdit(r.getAttributeAsString(DiskFileField.FILE_NAME.getEname()))){
			modelForm.hideItem(DiskFileField.CONTENT.getEname());
		}else{
			modelForm.showItem(DiskFileField.CONTENT.getEname());
		}
		fItem.disable();
		button.setTitle(constants.cwFormSaveButtonLabel());
		setHiddenItemValues();
	}
	
	@Override
	protected void newModel(){
		modelForm.editNewRecord();
		button.setTitle(constants.cwFormCreateButtonLabel());
		setHiddenItemValues();
		fItem.enable();
		button.setTitle(constants.cwFormCreateButtonLabel());
	}
	
	private void setHiddenItemValues(){
		modelForm.setValue(DiskFileField.SITE_ID.getEname(), siteId);
		modelForm.setValue(DiskFileField.DIR_PATH.getEname(), dirPath);
		modelForm.setValue(DiskFileField.OLD_FILE_NAME.getEname(), oldFileName);
	}
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight("80%");
		modelForm.setNumCols(1);
		modelForm.setDataSource(dfds);
		TextItem fnItem = new TextItem(DiskFileField.FILE_NAME.getEname(),DiskFileField.FILE_NAME.getCname());
		fnItem.setTitleOrientation(TitleOrientation.TOP);
		contentItem = new TextAreaItem(DiskFileField.CONTENT.getEname(),DiskFileField.CONTENT.getCname());
		fItem = new CheckboxItem(DiskFileField.IS_FOLDER.getEname(), DiskFileField.IS_FOLDER.getCname());
		fItem.setTitleOrientation(TitleOrientation.TOP);
		HiddenItem siteIdItem = new HiddenItem(DiskFileField.SITE_ID.getEname());
		HiddenItem dirPathItem = new HiddenItem(DiskFileField.DIR_PATH.getEname());
		HiddenItem oldFileItem = new HiddenItem(DiskFileField.OLD_FILE_NAME.getEname());
		HiddenItem idItem = new HiddenItem(CommonField.ID.getEname());
		contentItem.setWidth("800");
		contentItem.setHeight("450");
		contentItem.setTitleOrientation(TitleOrientation.TOP);
		modelForm.setItems(fItem,fnItem,contentItem,siteIdItem,dirPathItem,oldFileItem,idItem);
		fItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if((Boolean)(event.getValue())){
					modelForm.hideItem(DiskFileField.CONTENT.getEname());
					isFolder = true;
				}else{
					modelForm.showItem(DiskFileField.CONTENT.getEname());
					isFolder = false;
				}
				
			}
		});
	}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initLayout();
			initialized = true;
		}
		siteId = auService.getStringIdPara(paras, 0);
		dirPath = auService.getStringIdPara(paras, 1);
		switch (va) {
		case EDIT:
			setEdit(true);
			fileName = auService.getStringIdPara(paras, 2);
			oldFileName = fileName;
			isFolder = auService.getBooleanPara(paras, 3);
			String rid = auService.getStringIdPara(paras, 4);
			Map<String, String> m = auService.getDiskFileMap(siteId, dirPath, fileName, "", "", isFolder,rid);
			RPCRequest rr = auService.getDiskFileRpcRequest(m, SmartOperationName.CUSTOM.getValue(), SmartSubOperationName.FETCH_ONE.toString(), "GET");
			RPCManager.sendRequest(rr,new MyRpcCallback() {
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
	protected String getModelName() {
		return DiskFileDataSource.className;
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.DISKFILE_EDIT;
	}


}

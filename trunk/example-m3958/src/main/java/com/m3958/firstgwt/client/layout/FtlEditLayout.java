package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.FtlDataSource;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.FtlField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class FtlEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private FtlDataSource fds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		
	    TextItem nameItem = new TextItem(FtlField.NAME.getEname(),FtlField.NAME.getCname());
	    TextItem descriptionItem = new TextItem(FtlField.DESCRIPTION.getEname(),FtlField.DESCRIPTION.getCname());
	    
	    TextAreaItem ftlItem = new TextAreaItem(FtlField.FTL.getEname(),FtlField.FTL.getCname());
	    ftlItem.setColSpan(4);
	    ftlItem.setWidth(450);
	    ftlItem.setHeight(250);
	    
	    HiddenItem updatedAtItem = new HiddenItem(FtlField.UPDATEDAT.getEname());
	    updatedAtItem.setDisabled(true);
	    
		modelForm.setWidth100();
		modelForm.setHeight100();
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(fds);
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),nameItem,descriptionItem,auiService.getCreatedAtHiddenItem(),updatedAtItem,ftlItem);

	}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initLayout();
			initialized = true;
		}
		switch (va) {
		case EDIT:
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
			break;
		case ADD:
			newModel();
			break;
		default:
			break;
		}
	}

	@Override
	protected String getModelName() {
		return FtlDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FTL_EDIT;
	}

}

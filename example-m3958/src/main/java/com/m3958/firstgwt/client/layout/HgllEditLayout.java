package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.HgllDatasource;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;

public class HgllEditLayout extends MySimpleEditLayout{
	
	@Inject
	private HgllDatasource hds;

	private Label zpl = new Label();

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setDataSource(hds);
	}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initLayout();
			initialized = true;
		}
		switch (va) {
		case EDIT:
			Record r = srservice.getRecord(auService.getStringIdPara(paras,0)); 
			if(r != null){
				editModel(r);
				setZplConent(r);
			}else{
				RPCManager.sendRequest(auService.getFetchOneRpcRequest(auService.getStringIdPara(paras,0), getModelName()), new MyRpcCallback() {
					@Override
					public void afterSuccess(RPCResponse response, Object rawData,
							RPCRequest request, JSONValue data) {
						JSONArray ja = data.isArray();
						if(ja.size() == 1){
							Record r = new Record(ja.get(0).isObject().getJavaScriptObject());
							editModel(r);
							setZplConent(r);
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
	
	private void setZplConent(Record r){
		String zps = r.getAttributeAsString("zps");
		String[] ss = zps.split(",");
		String result = "";
		int i = 1;
		for(String s: ss){
			if(!s.isEmpty()){
				result = result + "<a href=\"/asset/" + s + "\" target=\"_blank\">照片" + i++ + "</a><br/>";
			}
		}
		zpl.setContents(result);
	}
	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.HGLL_EDIT;
	}

	@Override
	protected String getModelName() {
		return HgllDatasource.className;
	}

	@Override
	protected void initOtherWidget() {
		addMember(zpl);
	}

}

package com.m3958.firstgwt.client.window;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.datasource.RoleDataSource;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class roleTransferBar extends VLayout{
	
	@Inject
	private HasRoleGrid leftGrid;
	
	private ListGrid rightGrid;
	
	private String oid;
	
	private String modelName;

	public roleTransferBar(){
		setHeight100();
		setWidth(20);
		setMembersMargin(5);
		setAlign(VerticalAlignment.CENTER);
		addMember(initLeftArrowButton());
		addMember(initRightArrowButton());
	}
	
	
	public void setup(String oid,String modelName,ListGrid rightGrid){
		this.oid = oid;
		this.modelName = modelName;
		this.rightGrid = rightGrid;
	}
	
	
	private TransferImgButton initRightArrowButton() {
		TransferImgButton arrowButton = new TransferImgButton(TransferImgButton.RIGHT);
		arrowButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Record[] rs = leftGrid.getSelection();
            	if(rs.length == 0){
            		SC.say("请选择要删除的行！");
            		return;
            	}
            	RPCManager.sendRequest(getRpcRequest(oid,rs, "false"),new MyRpcCallback(){
					@Override
					public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
						leftGrid.invalidateCache();
					}});
            	}
			});
		return arrowButton;		
	}
	
	private TransferImgButton initLeftArrowButton() {
		TransferImgButton arrowButton = new TransferImgButton(TransferImgButton.LEFT);
		arrowButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Record[] rs = rightGrid.getSelection();
            	if(rs.length == 0){
            		SC.say("请选择要添加的行！");
            		return;
            	}
            	RPCManager.sendRequest(getRpcRequest(oid,rs, "true"),new MyRpcCallback(){
					@Override
					public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
						leftGrid.invalidateCache();
					}});
            	}
			});
		return arrowButton;
	}
	
	
	private RPCRequest getRpcRequest(String oid,Record[] roles,String b){
    	String rids = "";
    	for(int i=0;i<roles.length;i++){
    		rids = rids + roles[i].getAttribute("id") + ",";
    	}
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.MANAGE_RELATION.toString());
    	m.put(SmartParameterName.RELATION_MODEL_NAME, RoleDataSource.className);
    	m.put(SmartParameterName.EXTRAPARAS, rids);
    	m.put(SmartParameterName.MODEL_ID, oid);
    	m.put(SmartParameterName.MODEL_NAME, modelName);
    	m.put(SmartParameterName.IS_ADD_RELATION, b);
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
   		m.put(SmartParameterName.IS_MASTER, "1");
    	
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("POST");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
}

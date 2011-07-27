package com.m3958.firstgwt.client.window;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.UserDataSource;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class ShareToUserWindow extends Window{

	private String objectIdentity;
	private String modelName;
	private ListGrid sharedUserGrid;
	private DynamicForm shareForm;
	
	@Inject
	private AppUiService auservice;
	
	@Inject
	private VblockService bservice;
	
	private UserDataSource uds;
	
	@Inject
	public ShareToUserWindow(UserDataSource uds){
		this.uds = uds;
		setWidth(600);
		setHeight(450);
		setIsModal(false);
		centerInPage();
		init();
	}
	
	public void showMe(String oid,String modelName,String oname){
		setTitle("共享對象：" + oname);
		this.objectIdentity = oid;
		this.modelName = modelName;
		show();
		fetchData();
	}
	
	private void fetchData() {
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, modelName);
		c.addCriteria(SmartParameterName.RELATION_MODEL_ID,objectIdentity);
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.FETCH_SHARED_USERS.toString());
		sharedUserGrid.filterData(c);
	}
	
	
	//将某个对象的某个角色赋给某个用户。oid，ObjectRoleName，userid or email or loginName.
	private RPCRequest getRpcRequest(boolean isAsign){
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.SHARE_TO_USER.toString());
    	m.put(SmartParameterName.MODEL_ID, objectIdentity + "");
    	m.put(SmartParameterName.MODEL_NAME, modelName);
    	m.put(SmartParameterName.EXTRAPARAS, shareForm.getValueAsString("uid") + "," + shareForm.getValueAsString("orn"));
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
    	m.put(SmartParameterName.ADD_OR_REMOVE, String.valueOf(isAsign));
		m.put(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());

    	if(rollOverRecord != null){
    		m.put("rid", rollOverRecord.getAttributeAsString("rid"));
    		m.put("uid", rollOverRecord.getAttributeAsString("id"));
    	}
    		
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("POST");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	

	private void init(){
		VLayout layout = new VLayout(5);
		layout.addMember(initSharedUserGrid());
		layout.addMember(initShareForm());
		layout.addMember(initBt());
		addItem(layout);
	}

	private Canvas initBt() {
		IButton ib = new IButton("执行共享");
		ib.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(shareForm.valuesAreValid(false)){
	            	RPCManager.sendRequest(getRpcRequest(true),new MyRpcCallback(){
						@Override
						public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
//							boolean b = data.isBoolean().booleanValue();
//							if(b){
								auservice.showTmpPrompt("共享成功！");
								sharedUserGrid.invalidateCache();
//							}else{
//								auservice.showTmpPrompt("可能已經共享了！");
//							}
						}});
				}
			}
		});
		return ib;
	}

	private Canvas initShareForm() {
		shareForm = new DynamicForm();
		shareForm.setNumCols(4);
		TextItem userNameItem = new TextItem("uid","用户名");
		userNameItem.setRequired(true);
		SelectItem shareLevelItem = new SelectItem("orn","共享角色");
		shareLevelItem.setRequired(true);
		LinkedHashMap<String, String> orns = new LinkedHashMap<String, String>();
		ObjectRoleName[] t = ObjectRoleName.values();
		for(ObjectRoleName or:t){
			orns.put(or.toString(), or.getDisplayName());
		}
		shareLevelItem.setValueMap(orns);
		shareForm.setFields(userNameItem,shareLevelItem);
		return shareForm;
	}
	
    private HLayout rollOverCanvas;
    private ListGridRecord rollOverRecord;

	private Canvas initSharedUserGrid() {
		sharedUserGrid = new ListGrid(){
        @Override
        protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
            rollOverRecord = this.getRecord(rowNum);
            if(rollOverCanvas == null) {
                rollOverCanvas = new HLayout(3);
                rollOverCanvas.setSnapTo("TR");
                rollOverCanvas.setWidth(50);
                rollOverCanvas.setHeight(22);

                ImgButton editImg = new ImgButton();
                editImg.setShowDown(false);
                editImg.setShowRollOver(false);
                editImg.setLayoutAlign(Alignment.CENTER);
                editImg.setSrc("icons/16/close.png");
                editImg.setPrompt("取消共享");
                editImg.setHeight(16);
                editImg.setWidth(16);
                editImg.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                    	SC.confirm("取消共享？", new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if(value){
			    	            	RPCManager.sendRequest(getRpcRequest(false),new MyRpcCallback(){
			    						@Override
			    						public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
			    							boolean b = false;
			    							if(data.isArray() != null && data.isArray().size() == 1){
			    								b = true;
			    							}
			    							if(b){
			    								auservice.showTmpPrompt("共享已取消！");
			    								sharedUserGrid.invalidateCache();
			    							}else{
			    								auservice.showTmpPrompt("可能本来就没有共享！");
			    							}
			    						}});									
								}
								
							}
						});
                    }
                });
                rollOverCanvas.addMember(editImg);
            }
            return rollOverCanvas;

        }
    };
    
    sharedUserGrid.setShowRollOverCanvas(true);
		sharedUserGrid.setDataSource(uds);
		sharedUserGrid.setAutoFetchData(false);
		ListGridField idField = new ListGridField("id");
		idField.setHidden(true);
		ListGridField mainNameField = new ListGridField("mainName","用戶名");
		ListGridField subNameField = new ListGridField("subName","電郵");
		ListGridField roleIdField = new ListGridField("rid");
		roleIdField.setHidden(true);
		ListGridField roleNameField = new ListGridField("roleName","角色");
		
		sharedUserGrid.setFields(idField,mainNameField,subNameField,roleNameField,roleIdField);
		return sharedUserGrid;
	}
}

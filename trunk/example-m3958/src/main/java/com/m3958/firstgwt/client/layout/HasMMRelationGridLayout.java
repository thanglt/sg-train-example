package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONValue;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

public abstract class HasMMRelationGridLayout extends MyVlayout implements Iview,ICanRefresh{

	protected String selectedRecordId;
	
	protected String relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	protected ListGrid grid;
	
	public Btname[] btsNoAdd0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btsNoAdd1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH};
	public Btname[] btsNoAdd2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	public Btname[] simpleBts0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] simpleBts1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH};
	public Btname[] simpleBts2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	
	protected SelectionChangedHandler getSelectionChangeHandler(){
		return new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				Record[] rs = event.getSelection();
				if(rs.length == 1){
					selectedRecordId = rs[0].getAttributeAsString("id");
					srservice.putRecord(selectedRecordId,rs[0]);
				}
				
				for(Record r:rs){
					r.setAttribute(CommonField.RS_MODENAME.getEname(), getModelName());
				}
				RecordEvent re = new RecordEvent(getViewName(), RecordEventType.SELECT,auService.getRecordsIds(rs));
				eventBus.fireEvent(re);
			}
		};
	}
	
	protected abstract void initGrid();
	
	@Override
	public void setUnInitialized() {
		if(grid != null){
			removeMember(grid);
			grid = null;
			relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
		}
		initialized = false;
	}
	
	protected String lastFetchType;
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			initGrid();
			addMember(grid);
			initialized = true;
		}
		
		switch (va) {
		case REMOVE:
			grid.removeSelectedData();
			break;
		case CONTAINER_CLICKED:
			String id = auService.getStringIdPara(paras, 0);
			if(id == null || !id.equals(relationModelId)){
				relationModelId = id;
				fetchGridData();
			}
			break;
		case LEAVE_FOLDER:
			leaveRelation();
			break;
		case MINE:
			fetchMine();
			break;
		case OTHERS:
			fetchOthers();
			break;
		case GROUPS:
			fetchGroups();
			break;
		case SHOW_ALL:
			fetchAll();
			relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
			break;
		default:
//			if(auService.getStringIdPara(paras, 0).equals(SmartConstants.NONE_EXIST_MODEL_ID_STR) && !relationModelId.equals(SmartConstants.NONE_EXIST_MODEL_ID_STR)){
//				relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
//				fetchGridData();
//			}
//			if(auService.getStringIdPara(paras, 0).equals(SmartConstants.NONE_EXIST_MODEL_ID_STR) && relationModelId.equals(SmartConstants.NONE_EXIST_MODEL_ID_STR)){
//				relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
//				if("fetchMine".equals(lastFetchType)){
//					;
//				}else{
//					fetchMine();
//					lastFetchType = "fetchMine";
//				}
//			}
			break;
		}
	}
	
	public void fetchGridData(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,getMasterSideProperty());
		c.addCriteria(SmartParameterName.RELATION_MODEL_NAME,getRelationModelName());
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.MANY_TO_MANY.toString());
		c.addCriteria(SmartParameterName.RELATION_MODEL_ID, relationModelId);
		c.addCriteria(SmartParameterName.IS_MASTER,isMaster());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
	
	protected void leaveRelation() {
		if(relationModelId == null){
			SC.warn("必须在左边选定的情况下，才能离开这个类别！");
			return;
		}
		Record[] rs = grid.getSelection(); 
		if(rs.length < 1)return;
    	RPCManager.sendRequest(auService.getManageRelationRpcRequest(getRelationModelName(),relationModelId,!isMaster(),getModelName(), rs,false,getRelationHint()),new MyRpcCallback(){
			@Override
			public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
				if(data.isArray().size() > 0){
					auiService.showTmpPrompt("已退出该分类！");
					grid.invalidateCache();
				}
			}});
	}
	
	
	public abstract String getModelName();
	
	public abstract String getRelationModelName();
	
	public abstract String getMasterSideProperty();
	
	public abstract boolean isMaster();
	
	public abstract String getRelationHint();
	
	
	public void fetchMine(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.FETCH_OWNER,true);
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
	
	public void fetchOthers(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.NOT_CREATOR_HAS_PERMISSION.toString());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
	
	public void fetchGroups(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.NOT_CREATOR_HAS_GPERMISSION.toString());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
	
	protected void fetchAll(){
		if(aservice.isSuperman()){
			Criteria c = new Criteria();
			c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
			grid.filterData(c);
		}
	}
	
	
	@Override
	public Btname[] getStripStatus() {
		if(aservice.isSuperman()){
			if(SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(relationModelId)){
				if(grid == null)return new Btname[]{Btname.SHOW_ALL};
				ListGridRecord[] rs = grid.getSelection();
				if(rs.length == 0){
					return new Btname[]{Btname.SHOW_ALL};
				}else if(rs.length == 1){
					return btsNoAdd1;
				}else{
					return btsNoAdd2;
				}
			}else{
				if(grid == null)return new Btname[]{Btname.ADD,Btname.SHOW_ALL};
				ListGridRecord[] rs = grid.getSelection();
				if(rs.length == 0){
					return new Btname[]{Btname.ADD,Btname.SHOW_ALL};
				}else if(rs.length == 1){
					return simpleBts1;
				}else{
					return simpleBts2;
				}
			}
		}else{
			if(SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(relationModelId)){
				if(grid == null)return btsNoAdd0;
				ListGridRecord[] rs = grid.getSelection();
				if(rs.length == 0){
					return btsNoAdd0;
				}else if(rs.length == 1){
					return btsNoAdd1;
				}else{
					return btsNoAdd2;
				}
			}else{
				if(grid == null)return simpleBts0;
				ListGridRecord[] rs = grid.getSelection();
				if(rs.length == 0){
					return simpleBts0;
				}else if(rs.length == 1){
					return simpleBts1;
				}else{
					return simpleBts2;
				}
			}
		}

	}
	
	@Override
	public String[] getParas() {
		return new String[]{relationModelId,selectedRecordId};
	}
	
	
	@Override
	public void refreshContent() {
		grid.invalidateCache();
	}
}

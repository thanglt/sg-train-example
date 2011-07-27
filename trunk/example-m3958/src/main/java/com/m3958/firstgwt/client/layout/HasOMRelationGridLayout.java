package com.m3958.firstgwt.client.layout;

import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

public abstract class HasOMRelationGridLayout extends MyVlayout implements Iview,ICanRefresh{

	protected String selectedRecordId;
	
	protected String relationModelId;
	
	protected ListGrid grid;
	
	protected SelectionChangedHandler getSelectionChangeHandler(){
		return new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				Record[] rs = event.getSelection();
				if(rs.length == 1){
					selectedRecordId = rs[0].getAttributeAsString("id");
					srservice.putRecord(selectedRecordId,rs[0]);
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
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			initGrid();
			addMember(grid);
			initialized = true;
			firstCall = true;
		}
		String id;
		switch (va) {
		case REMOVE:
			grid.removeSelectedData();
			break;
		case CONTAINER_CLICKED:
			id = auService.getStringIdPara(paras, 0);
			if(id == null || !id.equals(relationModelId)){
				relationModelId = id;
				fetchGridData();
			}
			break;
		case NEXTVIEW:
			id = auService.getStringIdPara(paras, 0);
			if(id == null || !id.equals(relationModelId)){
				relationModelId = id;
				fetchGridData();
			}
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
			break;
		}
	}
	
	public void fetchGridData(){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME,getRelationModelName());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, relationModelId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, getMasterSideProperty());
	    c.addCriteria(SmartParameterName.IS_MASTER,isMaster());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    grid.filterData(c);
	}
	
	public abstract void fetchMine();
	
	public abstract void fetchOthers();
	
	public abstract void fetchGroups();
	
	public abstract void fetchAll();
	
	public abstract String getModelName();
	
	public abstract String getRelationModelName();
	
	public abstract String getMasterSideProperty();
	
	public abstract boolean isMaster();
	
	public abstract String getRelationHint();
	
	public Btname[] btsShowAll0 = new Btname[]{Btname.ADD,Btname.REFRESH,Btname.SHOW_ALL};
	public Btname[] btsShowAll1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.SHOW_ALL};
	public Btname[] btsShowAll2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH,Btname.SHOW_ALL};
	
	public Btname[] simpleBts0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] simpleBts1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH};
	public Btname[] simpleBts2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	
	protected void fetchAllDefault(){
		if(aservice.isSuperman()){
			Criteria c = new Criteria();
			grid.filterData(c);
		}
	}
	
	
	@Override
	public Btname[] getStripStatus() {
		if(aservice.isSuperman()){
			if(grid == null)return btsShowAll0;
			ListGridRecord[] rs = grid.getSelection();
			if(rs.length == 0){
				return btsShowAll0;
			}else if(rs.length == 1){
				return btsShowAll1;
			}else{
				return btsShowAll2;
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
	
	@Override
	public String[] getParas() {
		return new String[]{relationModelId,selectedRecordId};
	}
	
	
	@Override
	public void refreshContent() {
		grid.invalidateCache();
	}
}

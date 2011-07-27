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

public abstract class MySimpleGridLayout extends MyVlayout implements Iview,ICanRefresh{
	
	protected String selectedRecordId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	protected ListGrid grid;
	
	
	public Btname[] simpleBts0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] simpleBts1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH};
	public Btname[] simpleBts2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	
	protected SelectionChangedHandler getSelectionChangedHandler(){
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
			Criteria c = new Criteria();
			c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.HAS_PERMISSION.toString());
			grid.fetchData(c);
		}
	}
	
	
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			initGrid();
			addMember(grid);
			initialized = true;
			fetchGridData();
		}
		
		switch (va) {
		case REMOVE:
			grid.removeSelectedData();
			break;
		case SHOW_ALL:
			fetchAll();
			break;
		default:
			break;
		}
	}
	
	protected void fetchAll(){
		if(aservice.isSuperman()){
			Criteria c = new Criteria();
			grid.filterData(c);
		}
	}


	@Override
	public Btname[] getStripStatus() {
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
	
	@Override
	public String[] getParas() {
		return new String[]{selectedRecordId};
	}
	
	@Override
	public void refreshContent() {
		grid.invalidateCache();
		
	}
	
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.HAS_PERMISSION.toString());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}

}

package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.RoleDataSource;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.RoleField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;


@Singleton
public class RoleLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private RoleDataSource rds;



	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchData(false);
	    grid.setDataSource(rds);
	    grid.setShowFilterEditor(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    
	    
	    ListGridField ornameField = new ListGridField(RoleField.ORNAME.getEname(),RoleField.ORNAME.getCname());
	    ListGridField cnameField = new ListGridField(RoleField.CNAME.getEname(),RoleField.CNAME.getCname());

	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),cnameField,ornameField,auiService.getCreatedAtField(false));
	    
	    grid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				Record[] rs = event.getSelection();
				if(rs.length == 1){
					selectedRecordId = rs[0].getAttributeAsString("id");
					srservice.putRecord(selectedRecordId, rs[0]);
				}
				RecordEvent re = new RecordEvent(getViewName(), RecordEventType.SELECT);
				eventBus.fireEvent(re);
			}
		});
	}
	
	@Override
	public void fetchGridData(){
        String creatorIdsStr = "";
        if(!aservice.isSuperman()){
        	creatorIdsStr = "," + aservice.getSu().getId() + ",";
        }
		Criteria c = new Criteria(SmartParameterName.CREATORIDS,creatorIdsStr);
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
	
	@Override
	public Btname[] getStripStatus() {
		if(grid == null)return new Btname[]{Btname.ADD};
		ListGridRecord[] rs = grid.getSelection();
		if(rs.length == 0){
			return new Btname[]{Btname.ADD};
		}else if(rs.length == 1){
			return new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.PERMISSION};
		}else{
			return new Btname[]{Btname.ADD,Btname.REMOVE};
		}
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ROLE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.ROLE_EDIT;
	}
	
	public String getRid(){
		return selectedRecordId;
	}
	
	public String getRoleTitle(){
		if(grid.getSelectedRecord() != null){
			return grid.getSelectedRecord().getAttributeAsString(RoleField.CNAME.getEname());
		}else{
			return "";
		}
	}


}

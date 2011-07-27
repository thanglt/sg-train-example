package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.DepartmentDataSource;
import com.m3958.firstgwt.client.datasource.ShequDataSource;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ShequField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.Layout;

@Singleton
public class ShequLayout extends HasOMRelationGridLayout implements Iview {
	
	@Inject
	private ShequDataSource sds;
	

	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);

	    grid.setDataSource(sds);
	    grid.setAutoFetchData(false);
	    
	    ListGridField nameField = new ListGridField(ShequField.NAME.getEname(), ShequField.NAME.getCname());
	    ListGridField dizhiField = new ListGridField(ShequField.DIZHI.getEname(), ShequField.DIZHI.getCname());
	    dizhiField.setHidden(true);
	    ListGridField dianhuaField = new ListGridField(ShequField.DIANHUA.getEname(), ShequField.DIANHUA.getCname());
	    dianhuaField.setHidden(true);
	    ListGridField sqfzrField = new ListGridField(ShequField.SQFZR.getEname(), ShequField.SQFZR.getCname());
	    sqfzrField.setHidden(true);
	    ListGridField shoujiField = new ListGridField(ShequField.SHOUJI.getEname(), ShequField.SHOUJI.getCname());
	    shoujiField.setHidden(true);
	    
	    grid.setFields(auiService.getIdField(true),nameField,dizhiField,dianhuaField,sqfzrField,shoujiField,auiService.getCreatedAtField(false));
	    
	    grid.addSelectionChangedHandler(new SelectionChangedHandler() {
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
		});
	}
	

	public ListGrid getUserListGrid() {
		return grid;
	}


	@Override
	public Layout asLayout() {
		return this;
	}

	@Override
	public Btname[] getStripStatus() {
		if(grid == null)return new Btname[]{Btname.ADD};
		ListGridRecord[] rs = grid.getSelection();
		if(rs.length == 0){
			return new Btname[]{Btname.ADD};
		}else if(rs.length == 1){
			return new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT};
		}else{
			return new Btname[]{Btname.ADD,Btname.REMOVE};
		}
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.SHEQU;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case EDIT:
			return ViewNameEnum.SHEQU_EDIT;
		case ADD:
			return ViewNameEnum.SHEQU_EDIT;
		default:
			break;
		}
		return null;
	}


	@Override
	public void fetchGroups() {
		
	}

	@Override
	public void fetchMine() {
		
	}

	@Override
	public void fetchOthers() {
		
	}

	@Override
	public void fetchAll() {
		
	}

	@Override
	public String getModelName() {
		return ShequDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return DepartmentDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return ShequField.DEPARTMENT.getEname();
	}

	
	@Override
	public boolean isMaster() {
		return true;
	}

	@Override
	public String getRelationHint() {
		return ShequLayout.class.getName();
	}



}

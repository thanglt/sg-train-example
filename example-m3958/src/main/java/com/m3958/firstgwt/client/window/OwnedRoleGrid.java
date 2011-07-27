package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.datasource.RoleDataSource;
import com.m3958.firstgwt.client.types.RoleField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class OwnedRoleGrid extends ListGrid{
	
	@Inject
	private AppStatusService auservice;
	
	@Inject
	public OwnedRoleGrid(RoleDataSource rds){
        setWidth100();
        setHeight100();
        setAutoFetchData(false);
        
        setDataSource(rds);
        setCanDragRecordsOut(true);
        setDragDataAction(DragDataAction.COPY);

	    setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier("createdAt",SortDirection.DESCENDING)};
	    setInitialSort(ssf);
        ListGridField roleIdField = new ListGridField("id");
        roleIdField.setHidden(true);
        ListGridField nameField = new ListGridField(RoleField.CNAME.getEname(),RoleField.CNAME.getCname());
        setFields(roleIdField, nameField);
	}
	
	public void myFetchData(){
        String creatorIdsStr = "";
        if(!auservice.isSuperman()){
        	creatorIdsStr = "," + auservice.getSu().getId() + ",";
        }
        Criteria c = new Criteria(SmartParameterName.CREATORIDS,creatorIdsStr);
        filterData(c);
	}
}

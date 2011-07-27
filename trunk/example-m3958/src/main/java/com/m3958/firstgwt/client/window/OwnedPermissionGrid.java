package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.datasource.PermissionDataSource;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.types.PermissionField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class OwnedPermissionGrid extends ListGrid{
	
	@Inject
	private AppStatusService auservice;
	
	@Inject
	public OwnedPermissionGrid(PermissionDataSource pds,AppUiService auiservice){
        setWidth100();
        setHeight100();
	    setShowRowNumbers(true);
	    setAutoFetchData(false);
	    setShowFilterEditor(true);
	    setFilterOnKeypress(false);
	    setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    setDataSource(pds);

        ListGridField opCodeField = new ListGridField(PermissionField.OPCODE.getEname(),PermissionField.OPCODE.getCname());
        opCodeField.setHidden(true);
        ListGridField nameField = new ListGridField(PermissionField.NAME.getEname(),PermissionField.NAME.getCname());
        setFields(auiservice.getIdField(true),nameField,opCodeField);

	}

	
	public void myFetchData(){
        String creatorIdsStr = "";
        if(!auservice.isSuperman()){
        	creatorIdsStr = "," + auservice.getSu().getId() + ",";
        }
        Criteria c = new Criteria(SmartParameterName.CREATORIDS,creatorIdsStr);
        c.addCriteria(SmartParameterName.TEXT_MATCHSTYLE, TextMatchStyle.SUBSTRING.getValue());
	    fetchData(c);
	}
}

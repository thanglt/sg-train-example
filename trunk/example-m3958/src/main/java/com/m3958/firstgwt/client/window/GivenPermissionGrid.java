package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.datasource.PermissionDataSource;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.types.PermissionExtraFields;
import com.m3958.firstgwt.client.types.PermissionField;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class GivenPermissionGrid extends ListGrid{
	
	@Inject
	private AppStatusService auservice;
	
	
	@Inject
	public GivenPermissionGrid(PermissionDataSource pds,AppUiService auiservice){
        setWidth100();
        setHeight100();
        setAutoFetchData(false);
        setCanAcceptDroppedRecords(true);
        setPreventDuplicates(true);
        setDataSource(pds);

        ListGridField opCodeField = new ListGridField(PermissionField.OPCODE.getEname(),PermissionField.OPCODE.getCname());
        opCodeField.setHidden(true);
        ListGridField nameField = new ListGridField(PermissionField.NAME.getEname(),PermissionField.NAME.getCname());
        setFields(auiservice.getIdField(true),nameField,opCodeField);
	}
	
	public void myFetchData(){
        Criteria c = new Criteria();
        c.addCriteria(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
        c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.CUSTOM_FETCH.toString());
        c.addCriteria(PermissionExtraFields.PERMISSION_OWNER_ID,auservice.getSu().getId());
	    fetchData(c);
	}
}

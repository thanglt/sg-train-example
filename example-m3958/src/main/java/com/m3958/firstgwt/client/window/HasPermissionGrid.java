package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.PermissionDataSource;
import com.m3958.firstgwt.client.datasource.RoleDataSource;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.types.PermissionField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class HasPermissionGrid extends ListGrid{
	
	@Inject
	public HasPermissionGrid(PermissionDataSource pds,AppUiService auiservice){
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

	
	public void myFetchData(String rid){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.MANY_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,"permissions");
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME,RoleDataSource.className);
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID,rid);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    filterData(c);
	}
}

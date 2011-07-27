package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.RoleDataSource;
import com.m3958.firstgwt.client.types.RoleField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class HasRoleGrid extends ListGrid{
	
	@Inject
	public HasRoleGrid(RoleDataSource rds){
	    setWidth100();
	    setHeight100();
	    setAutoFetchData(false);
	    setCanAcceptDroppedRecords(true);
	    setPreventDuplicates(true);
	    setDataSource(rds);
	    ListGridField roleGridField2 = new ListGridField("id");
	    roleGridField2.setHidden(true);
	    ListGridField nameField2 = new ListGridField(RoleField.CNAME.getEname(),RoleField.CNAME.getCname());
	    setFields(roleGridField2, nameField2);
	}

	
	public void myFetchData(String oid,String modelName){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.MANY_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,"roles");
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME,modelName);
    	c.addCriteria(SmartParameterName.RELATION_MODEL_ID,oid);
    	c.addCriteria(SmartParameterName.IS_MASTER,"0");
	    filterData(c);
	}
}

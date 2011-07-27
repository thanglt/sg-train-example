package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.PermissionField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class PermissionDataSource extends BaseDataSource{

  
    public static String className = "com.m3958.firstgwt.model.Permission";
    
    @Inject
    public PermissionDataSource(DsErrorHandler deh) {
    	setID("permissionDS");
    	
    	
        
        DataSourceTextField nameField = new DataSourceTextField(PermissionField.NAME.getEname(),PermissionField.NAME.getCname());
        DataSourceTextField opCodeField = new DataSourceTextField(PermissionField.OPCODE.getEname(),PermissionField.OPCODE.getCname());
        
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),getVersionField(),nameField,opCodeField,getCreatedAtField(false));
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

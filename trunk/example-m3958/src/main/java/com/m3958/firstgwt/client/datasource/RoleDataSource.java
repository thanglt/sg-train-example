package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.RoleField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class RoleDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.Role";
    
    @Inject
    public RoleDataSource(DsErrorHandler deh) {
    	setID("roleDS");
    	
        DataSourceTextField orNameField = new DataSourceTextField(RoleField.ORNAME.getEname());
        DataSourceTextField cnameField = new DataSourceTextField(RoleField.CNAME.getEname());
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),orNameField,cnameField,getCreatedAtField(false),getVersionField(),getCreatorIdsField(),getSubFetchOperationTypeField(),getRelationPropertyNameField(),getRelationModelIdField());
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

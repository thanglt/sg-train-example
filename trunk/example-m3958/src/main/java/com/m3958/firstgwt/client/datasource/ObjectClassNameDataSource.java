package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ObjectClassNameField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class ObjectClassNameDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.ObjectClassName";
    
    @Inject
    public ObjectClassNameDataSource(DsErrorHandler deh) {
    	setID("objectClassDS");
    	
    	
        DataSourceTextField enameField = new DataSourceTextField(ObjectClassNameField.ENAME.getEname(), ObjectClassNameField.ENAME.getCname());
        DataSourceTextField cnameField = new DataSourceTextField(ObjectClassNameField.CNAME.getEname(), ObjectClassNameField.CNAME.getCname());

        DataSourceTextField daoNameField = new DataSourceTextField(ObjectClassNameField.DAO_NAME.getEname(),ObjectClassNameField.DAO_NAME.getCname());
        DataSourceTextField checkerNameField = new DataSourceTextField(ObjectClassNameField.CHECKER_NAME.getEname(),ObjectClassNameField.CHECKER_NAME.getCname());
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),enameField,cnameField,getCreatedAtField(false),daoNameField,checkerNameField,getVersionField());
        setClientOnly(false);

    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DepartmentField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class DepartmentDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Department";
    
    @Inject
    public DepartmentDataSource(DsErrorHandler deh) {
    	setID("departmentDS");
    	setTitleField("cname");
    	
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);
        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        
        DataSourceTextField cnameField = new DataSourceTextField(DepartmentField.NAME.getEname(),DepartmentField.NAME.getEname());
        
        DataSourceTextField bzField = new DataSourceTextField(DepartmentField.BZ.getEname(), DepartmentField.BZ.getCname());
        
        DataSourceBooleanField shequContainerField = new DataSourceBooleanField(DepartmentField.SHEQU_CONTAINER.getEname(),DepartmentField.SHEQU_CONTAINER.getCname());
        
        addHandleErrorHandler(deh);
        setFields(cnameField,getIdField(),getCreatedAtField(false),parentField,shequContainerField,getVersionField(),bzField);
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.types.FieldEnumField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;

@Singleton
public class FieldEnumDataSource extends BaseDataSource{
    
    public static String className = "com.m3958.firstgwt.model.FieldEnum";
    
    @Inject
    public FieldEnumDataSource(GlobalConstants gcontants,DsErrorHandler deh) {
    	setID("fieldEnumDS");
        DataSourceIntegerField positionField = new DataSourceIntegerField(FieldEnumField.POSITION.getEname());

        DataSourceEnumField fieldTypeField = new DataSourceEnumField(FieldEnumField.FIELD_TYPE.getEname());
        fieldTypeField.setType(FieldType.ENUM);
        fieldTypeField.setValueMap((gcontants.getAllLimitFieldNames()));
        DataSourceTextField fieldValueField = new DataSourceTextField(FieldEnumField.FIELD_VALUE.getEname());
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),fieldTypeField,fieldValueField,positionField,getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}   
}

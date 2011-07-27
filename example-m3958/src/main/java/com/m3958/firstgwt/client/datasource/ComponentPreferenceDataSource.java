package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ComponentPreferenceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class ComponentPreferenceDataSource extends BaseDataSource{
   
    public static String className = "com.m3958.firstgwt.model.ComponentPreference";
    
    @Inject
    public ComponentPreferenceDataSource(DsErrorHandler deh) {
    	setID("componentPreferenceDS");
    	
        DataSourceTextField nameField = new DataSourceTextField(ComponentPreferenceField.NAME.getEname());
        DataSourceTextField viewStateField = new DataSourceTextField(ComponentPreferenceField.VIEW_STATE.getEname());
        DataSourceTextField modelNameField = new DataSourceTextField(ComponentPreferenceField.MODEL_NAME.getEname());
        DataSourceBooleanField lastRequestField = new DataSourceBooleanField(ComponentPreferenceField.LAST_REQUEST.getEname());
        lastRequestField.setHidden(true);
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),nameField,viewStateField,modelNameField,getCreatedAtField(false),lastRequestField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

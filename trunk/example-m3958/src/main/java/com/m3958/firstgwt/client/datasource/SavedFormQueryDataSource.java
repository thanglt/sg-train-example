package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.SavedFormQueryField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class SavedFormQueryDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.SavedFormQuery";

    @Inject
    public SavedFormQueryDataSource(DsErrorHandler deh) {
    	setID("savedFormQeryDS");
    	

        DataSourceTextField nameField = new DataSourceTextField(SavedFormQueryField.NAME.getEname());
        DataSourceBooleanField queryStringField = new DataSourceBooleanField(SavedFormQueryField.QUERY_STRING.getEname());
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),nameField,queryStringField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

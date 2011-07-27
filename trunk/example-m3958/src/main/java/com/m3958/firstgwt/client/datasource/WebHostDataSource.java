package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.WebHostField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class WebHostDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.WebHost";
    
    @Inject
    public WebHostDataSource(DsErrorHandler deh) {
    	setID("webHostDS");
        DataSourceTextField nameField = new DataSourceTextField(WebHostField.NAME.getEname());
        DataSourceTextField styleField = new DataSourceTextField(WebHostField.THEME.getEname());
        DataSourceBooleanField auditField = new DataSourceBooleanField(CommonField.AUDIT.getEname(),CommonField.AUDIT.getCname());
        addHandleErrorHandler(deh);

        setFields(getIdField(),nameField,styleField,getCreatedAtField(false),getVersionField(),auditField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

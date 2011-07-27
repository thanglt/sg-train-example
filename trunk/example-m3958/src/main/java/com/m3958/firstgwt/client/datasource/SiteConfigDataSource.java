package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.SiteConfigField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class SiteConfigDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.SiteConfig";
    
    @Inject
    public SiteConfigDataSource(DsErrorHandler deh) {
    	
    	setID("siteConfigDS");
        
        DataSourceTextField keyField = new DataSourceTextField(SiteConfigField.CONFIG_KEY.getEname(),SiteConfigField.CONFIG_KEY.getCname());

        DataSourceTextField descriptionField = new DataSourceTextField(SiteConfigField.DESCRIPTION.getEname(),SiteConfigField.DESCRIPTION.getCname());
        
        DataSourceTextField valueField = new DataSourceTextField(SiteConfigField.CONFIG_VALUE.getEname(),SiteConfigField.CONFIG_VALUE.getCname());
        
        DataSourceBooleanField forClientField = new DataSourceBooleanField(SiteConfigField.CLIENT_SIDE.getEname(),SiteConfigField.CLIENT_SIDE.getCname());

        setFields(getIdField(),getVersionField(),forClientField,getCreatedAtField(false),keyField,descriptionField,valueField);
        
        addHandleErrorHandler(deh);
        
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

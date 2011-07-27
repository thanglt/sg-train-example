package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.PageMistakeField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class PageMistakeDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.PageMistake";
    
    @Inject
    public PageMistakeDataSource(DsErrorHandler deh) {
    	setID("pageMistakeDS");
        DataSourceTextField usernameField = new DataSourceTextField(PageMistakeField.USERNAME.getEname(),PageMistakeField.USERNAME.getCname());
        
        DataSourceTextField pageUrlField = new DataSourceTextField(PageMistakeField.PAGE_URL.getEname(),PageMistakeField.PAGE_URL.getCname());
        
        DataSourceTextField ipField = new DataSourceTextField(CommonField.IP.getEname(),CommonField.IP.getCname());
        
        DataSourceTextField lxfsField = new DataSourceTextField(PageMistakeField.LXFS.getEname(),PageMistakeField.LXFS.getCname());
        
        DataSourceTextField errorTypeField = new DataSourceTextField(PageMistakeField.ERROR_TYPE.getEname(),PageMistakeField.ERROR_TYPE.getCname());
        
        DataSourceTextField descriptionField = new DataSourceTextField(PageMistakeField.DESCRIPTION.getEname(),PageMistakeField.DESCRIPTION.getCname());
        descriptionField.setLength(1000);
        
        DataSourceBooleanField fixedField = new DataSourceBooleanField(PageMistakeField.FIXED.getEname(),PageMistakeField.FIXED.getCname());
        addHandleErrorHandler(deh);

        setFields(getIdField(),pageUrlField,errorTypeField,lxfsField,descriptionField,usernameField,getCreatedAtField(false),getVersionField(),fixedField,ipField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

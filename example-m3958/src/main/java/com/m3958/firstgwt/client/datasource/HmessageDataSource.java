package com.m3958.firstgwt.client.datasource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.HmessageField;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class HmessageDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.Hmessage";

    @Inject
    public HmessageDataSource(DsErrorHandler deh) {
    	setID("hmessageDS");
    	
    	DataSourceTextField statusField = new DataSourceTextField(HmessageReceiverField.STATUS.getEname(),HmessageReceiverField.STATUS.getCname());
    	
        DataSourceTextField feedbackTypeField = new DataSourceTextField(HmessageField.TITLE.getEname());
        
        DataSourceTextField messageField = new DataSourceTextField(HmessageField.MESSAGE.getEname());
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),feedbackTypeField,messageField,getCreatedAtField(false),statusField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}   
}

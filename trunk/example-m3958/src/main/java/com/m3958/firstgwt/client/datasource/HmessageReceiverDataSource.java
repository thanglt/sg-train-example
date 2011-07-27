package com.m3958.firstgwt.client.datasource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class HmessageReceiverDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.HmessageReceiver";

    @Inject
    public HmessageReceiverDataSource(DsErrorHandler deh) {
    	setID("hmReceiverDS");
    	
    	
        DataSourceTextField statusField = new DataSourceTextField(HmessageReceiverField.STATUS.getEname(),HmessageReceiverField.STATUS.getCname());
        DataSourceDateTimeField readTimeField = new DataSourceDateTimeField(HmessageReceiverField.READ_TIME.getEname(),HmessageReceiverField.READ_TIME.getCname());
        
        DataSourceTextField titleField = new DataSourceTextField(HmessageReceiverField.TITLE.getEname(),HmessageReceiverField.TITLE.getCname());
        
        DataSourceTextField msgBodyField = new DataSourceTextField(HmessageReceiverField.MSG_BODY.getEname(),HmessageReceiverField.MSG_BODY.getCname());
        
        DataSourceTextField senderField = new DataSourceTextField(HmessageReceiverField.SENDER.getEname(),HmessageReceiverField.SENDER.getCname());
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),statusField,readTimeField,senderField,titleField,msgBodyField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}   
}

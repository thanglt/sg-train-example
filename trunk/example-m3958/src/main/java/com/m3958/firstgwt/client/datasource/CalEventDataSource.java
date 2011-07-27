package com.m3958.firstgwt.client.datasource;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CalEventField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class CalEventDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.CalEvent";

    public CalEventDataSource(DsErrorHandler deh) {
    	setID("calEventDS");
    	
    	
        DataSourceTextField nameField = new DataSourceTextField(CalEventField.NAME.getEname(),CalEventField.NAME.getCname());
        DataSourceTextField descriptionField = new DataSourceTextField(CalEventField.DESCRIPTION.getEname(),CalEventField.DESCRIPTION.getCname());
        
        DataSourceEnumField calEventTypeField = new DataSourceEnumField(CalEventField.CALEVENT_TYPE.getEname(),CalEventField.CALEVENT_TYPE.getCname());
        
        DataSourceDateTimeField startField = new DataSourceDateTimeField(CalEventField.START_DATE.getEname(),CalEventField.START_DATE.getCname());
        DataSourceDateTimeField endField = new DataSourceDateTimeField(CalEventField.END_DATE.getEname(),CalEventField.END_DATE.getCname());
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),getVersionField(),nameField,descriptionField,startField,endField,calEventTypeField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

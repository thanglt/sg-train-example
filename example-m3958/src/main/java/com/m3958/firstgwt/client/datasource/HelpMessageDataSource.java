package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.HelpId;
import com.m3958.firstgwt.client.types.HelpMessageField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;

@Singleton
public class HelpMessageDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.HelpMessage";


    @Inject
    public HelpMessageDataSource(DsErrorHandler deh) {
    	
    	
    	setID("helpMessageDS");
    	
        DataSourceEnumField helpIdField = new DataSourceEnumField(HelpMessageField.HELP_ID.getEname());
        helpIdField.setType(FieldType.ENUM);
        String[] ss = new String[HelpId.values().length];
        int i =0;
        for(HelpId hid: HelpId.values()){
        	ss[i++] = hid.toString();
        }
        helpIdField.setValueMap(ss);
        
        DataSourceTextField helpMessageField = new DataSourceTextField(HelpMessageField.HELP_MESSAGE.getEname());
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),helpIdField,helpMessageField,getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}    
}

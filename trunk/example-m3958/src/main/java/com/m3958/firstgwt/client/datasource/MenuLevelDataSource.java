package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.MenuLevelField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class MenuLevelDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.MenuLevel";
    
    @Inject
    public MenuLevelDataSource(DsErrorHandler deh) {
    	setID("menuLevelDS");
    	
        DataSourceTextField nameField = new DataSourceTextField(MenuLevelField.NAME.getEname());
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),nameField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

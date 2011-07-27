package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class MenuItemDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.MenuItem";
    
    @Inject
    public MenuItemDataSource(DsErrorHandler deh) {
    	setID("menuitemDS");
    	
        DataSourceTextField titleField = new DataSourceTextField(MenuItemField.TITLE.getEname());

        DataSourceTextField uniqueNameField = new DataSourceTextField(MenuItemField.UNIQUE_NAME.getEname());
        
        DataSourceTextField menuItemCatField = new DataSourceTextField(MenuItemField.MENUITEMCAT.getEname());
         
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),uniqueNameField,menuItemCatField,titleField,getCreatedAtField(false),getPositionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

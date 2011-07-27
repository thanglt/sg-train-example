package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ShequField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class ShequDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Shequ";
    
    @Inject
    public ShequDataSource(DsErrorHandler deh) {
    	setID("shequDS");
        
        
        DataSourceTextField bzField = new DataSourceTextField(ShequField.BZ.getEname(), ShequField.BZ.getCname());
        DataSourceTextField nameField = new DataSourceTextField(ShequField.NAME.getEname(), ShequField.NAME.getCname());
        
        DataSourceTextField dizhiField = new DataSourceTextField(ShequField.DIZHI.getEname(), ShequField.DIZHI.getCname());
        
        DataSourceTextField sqfzrField = new DataSourceTextField(ShequField.SQFZR.getEname(), ShequField.SQFZR.getCname());
        
        DataSourceTextField dianhuaField = new DataSourceTextField(ShequField.DIANHUA.getEname(), ShequField.DIANHUA.getCname());
        
        DataSourceTextField shoujiField = new DataSourceTextField(ShequField.SHOUJI.getEname(), ShequField.SHOUJI.getCname());
        
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),getVersionField(),nameField,dizhiField,sqfzrField,dianhuaField,shoujiField,bzField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}  
}

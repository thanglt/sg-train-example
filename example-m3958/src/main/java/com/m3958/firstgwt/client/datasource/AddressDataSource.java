package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.AddressField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class AddressDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Address";
    
    @Inject
    public AddressDataSource(DsErrorHandler deh) {
    	setID("addressDS");
        DataSourceTextField dizhiField = new DataSourceTextField(AddressField.DIZHI.getEname());
        DataSourceBooleanField mainAddressField = new DataSourceBooleanField(AddressField.MAIN_ADDRESS.getEname());
        DataSourceTextField shequField = new DataSourceTextField(AddressField.SHEQU.getEname());
        DataSourceTextField sqlxrField = new DataSourceTextField(AddressField.SQLXR.getEname());
        DataSourceTextField dianhuaField = new DataSourceTextField(AddressField.DIANHUA.getEname());
        DataSourceTextField shoujiField = new DataSourceTextField(AddressField.SHOUJI.getEname());
        
        DataSourceTextField bzField = new DataSourceTextField(AddressField.BZ.getEname(), AddressField.BZ.getCname());
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),dizhiField,shequField,sqlxrField,dianhuaField,shoujiField,mainAddressField,bzField,getCreatedAtField(false));
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}

}

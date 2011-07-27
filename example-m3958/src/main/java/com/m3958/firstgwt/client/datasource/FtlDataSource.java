package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.FtlField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class FtlDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Ftl";
    
    @Inject
    public FtlDataSource(DsErrorHandler deh) {
    	
    	setID("ftlDS");
    	
        DataSourceDateTimeField updatedAtField = new DataSourceDateTimeField(FtlField.UPDATEDAT.getEname(),FtlField.UPDATEDAT.getCname());
        updatedAtField.setHidden(true);

        addHandleErrorHandler(deh);
        
        DataSourceTextField nameField = new DataSourceTextField(FtlField.NAME.getEname(),FtlField.NAME.getCname());

        DataSourceTextField descriptionField = new DataSourceTextField(FtlField.DESCRIPTION.getEname(),FtlField.DESCRIPTION.getCname());
        
        DataSourceTextField ftlField = new DataSourceTextField(FtlField.FTL.getEname(),FtlField.FTL.getCname());

        setFields(getIdField(),getVersionField(),getCreatedAtField(false),updatedAtField,nameField,descriptionField,ftlField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

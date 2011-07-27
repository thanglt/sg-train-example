package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class DiskFileDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.DiskFile";
    
    @Inject
    public DiskFileDataSource(DsErrorHandler deh) {
    	setID("diskFileDS");
        DataSourceIntegerField fileSizeField = new DataSourceIntegerField(DiskFileField.FILE_SIZE.getEname(),DiskFileField.FILE_SIZE.getCname());
        DataSourceBooleanField folderField = new DataSourceBooleanField(DiskFileField.IS_FOLDER.getEname(),DiskFileField.IS_FOLDER.getCname());
        DataSourceTextField nameField = new DataSourceTextField(DiskFileField.FILE_NAME.getEname(),DiskFileField.FILE_NAME.getCname());
        DataSourceTextField contentField = new DataSourceTextField(DiskFileField.CONTENT.getEname(),DiskFileField.CONTENT.getCname());
        DataSourceDateTimeField lastModifiedField = new DataSourceDateTimeField(DiskFileField.LAST_MODIFIED.getEname(),DiskFileField.LAST_MODIFIED.getCname());
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),fileSizeField,folderField,nameField,lastModifiedField,contentField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

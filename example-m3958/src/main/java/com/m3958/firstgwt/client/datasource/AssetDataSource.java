package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.AssetField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class AssetDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Asset";
    
    @Inject
    public AssetDataSource(DsErrorHandler deh) {
    	setID("assetDS");
    	
    	DataSourceTextField thumbnailField = new DataSourceTextField(AssetField.THUMBNAIL.getEname(),AssetField.THUMBNAIL.getCname());
        DataSourceTextField descriptionField = new DataSourceTextField(AssetField.DESCRIPTION.getEname(),AssetField.DESCRIPTION.getCname());
        DataSourceTextField filePathField = new DataSourceTextField(AssetField.FILE_PATH.getEname(),AssetField.FILE_PATH.getCname());
        DataSourceTextField fileIdField = new DataSourceTextField(AssetField.FILE_ID.getEname(),AssetField.FILE_ID.getCname());
        DataSourceIntegerField fileSizeField = new DataSourceIntegerField(AssetField.FILE_SIZE.getEname(),AssetField.FILE_SIZE.getCname());
        DataSourceTextField mimeTypeField = new DataSourceTextField(AssetField.MIME_TYPE.getEname(),AssetField.MIME_TYPE.getCname());
        DataSourceTextField originNameField = new DataSourceTextField(AssetField.ORIGIN_NAME.getEname(),AssetField.ORIGIN_NAME.getCname());
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),getSiteIdField(),thumbnailField,filePathField,fileIdField,fileSizeField,mimeTypeField,originNameField,descriptionField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
    
}

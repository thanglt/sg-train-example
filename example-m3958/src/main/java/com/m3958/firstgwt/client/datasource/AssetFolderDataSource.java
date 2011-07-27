package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.AssetFolderField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class AssetFolderDataSource extends BaseDataSource{

  
    public static String className = "com.m3958.firstgwt.model.AssetFolder";

    @Inject
    public AssetFolderDataSource(DsErrorHandler deh) {
    	setID("assetFolderDs");
    	setTitleField("name");
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);

        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        
        DataSourceTextField nameField = new DataSourceTextField(AssetFolderField.NAME.getEname(),AssetFolderField.NAME.getEname());
//        DataSourceBooleanField isFolderField = new DataSourceBooleanField(SmartConstants.HAS_CHILDREN);
//        isFolderField.setHidden(true);
        
        addHandleErrorHandler(deh);
        setFields(nameField,getIdField(),getCreatedAtField(false),parentField,getVersionField(),getPositionField(),getPerpageField());
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

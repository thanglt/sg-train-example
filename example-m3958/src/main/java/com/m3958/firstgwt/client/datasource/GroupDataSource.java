package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class GroupDataSource extends BaseDataSource{
   
    public static String className = "com.m3958.firstgwt.model.Group";

    @Inject
    public GroupDataSource(DsErrorHandler deh) {
    	setID("groupDs");
    	setTitleField(CommonField.NAME.getEname());
    	
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);

        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getEname());
        addHandleErrorHandler(deh);
        setFields(nameField,getIdField(),getCreatedAtField(false),parentField,getVersionField());
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}

}

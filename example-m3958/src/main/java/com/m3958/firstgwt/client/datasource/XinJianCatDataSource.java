package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SectionField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.XinJianCatField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class XinJianCatDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.XinJianCat";
    
    @Inject
    public XinJianCatDataSource(DsErrorHandler deh) {
    	setID("xinJianCatDS");
    	setTitleField(CommonField.NAME.getEname());

        
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);

        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        DataSourceBooleanField xjcatContainerField = new DataSourceBooleanField(XinJianCatField.XJCAT_CONTAINER.getEname(),XinJianCatField.XJCAT_CONTAINER.getCname());
        
        DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getEname());
        DataSourceTextField kvField = new DataSourceTextField(XinJianCatField.KEY_VALUES.getEname(),XinJianCatField.KEY_VALUES.getEname());
   
        addHandleErrorHandler(deh);
        setFields(nameField,getVisibleIdField(),getCreatedAtField(false),parentField,getVersionField(),kvField,getPositionField(),getTplNameField(),getPerpageField(),xjcatContainerField);
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}

}

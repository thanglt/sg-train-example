package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SectionField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class SectionDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.Section";
    
    @Inject
    public SectionDataSource(DsErrorHandler deh) {
    	setID("sectionDs");
    	setTitleField(CommonField.NAME.getEname());

        
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);

        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        
        DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getEname());
        DataSourceTextField kvField = new DataSourceTextField(CommonField.KEY_VALUES.getEname(),CommonField.KEY_VALUES.getEname());
        DataSourceTextField protectLevelField = new DataSourceTextField(CommonField.PROTECT_LEVEL.getEname(),CommonField.PROTECT_LEVEL.getEname());
        
        DataSourceIntegerField positionField = new DataSourceIntegerField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
        DataSourceBooleanField hiddenField = new DataSourceBooleanField(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
        DataSourceBooleanField sectionContainerField = new DataSourceBooleanField(SectionField.SECTION_CONTAINER.getEname(),SectionField.SECTION_CONTAINER.getCname());
   
        addHandleErrorHandler(deh);
        setFields(nameField,getIdField(),getSiteIdField(),getCreatedAtField(false),parentField,getVersionField(),kvField,positionField,hiddenField,getTplNameField(),getPerpageField(),sectionContainerField,protectLevelField);
        setClientOnly(false);
    }
    
	@Override
	protected String getModelClassName() {
		return className;
	}
}

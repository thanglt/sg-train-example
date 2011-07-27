package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LinkField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class LinkDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Link";
    
    @Inject
    public LinkDataSource(DsErrorHandler deh) {
    	setID("linkDS");
    	
    	setTitleField(CommonField.NAME.getEname());
    	
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);

        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        DataSourceTextField linkToField = new DataSourceTextField(LinkField.LINK_TO.getEname(),LinkField.LINK_TO.getCname());
        DataSourceTextField lurlField = new DataSourceTextField(LinkField.LURL.getEname(),LinkField.LURL.getCname());
        DataSourceTextField tplNameField = new DataSourceTextField(LinkField.TPL_NAME.getEname(),LinkField.TPL_NAME.getCname());
        DataSourceIntegerField modelIdNameField = new DataSourceIntegerField(LinkField.MODEL_ID.getEname(),LinkField.MODEL_ID.getCname());
        
        DataSourceBooleanField hiddenField = new DataSourceBooleanField(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
        
        DataSourceTextField kvField = new DataSourceTextField(CommonField.KEY_VALUES.getEname(),CommonField.KEY_VALUES.getEname());
         
        addHandleErrorHandler(deh);
        
        setFields(parentField,getSiteIdField(),hiddenField,getIdField(),getVersionField(),nameField,getCreatedAtField(false),getPositionField(),linkToField,lurlField,tplNameField,modelIdNameField,kvField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

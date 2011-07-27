package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.JrxmlFileField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class JrxmlFileDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.JrxmlFile";
    
    @Inject
    public JrxmlFileDataSource(DsErrorHandler deh) {
    	setID("jrxmlFileDS");
    	
        DataSourceIntegerField parentIdField = new DataSourceIntegerField(SmartParameterName.PARENTID);
        parentIdField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentIdField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);

        DataSourceTextField nameField = new DataSourceTextField(JrxmlFileField.NAME.getEname());
        DataSourceTextField jrxmlField = new DataSourceTextField(JrxmlFileField.JRXML.getEname());
        DataSourceTextField modelNameField = new DataSourceTextField(JrxmlFileField.MODEL_NAME.getEname());
         
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),nameField,jrxmlField,modelNameField,parentIdField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}   
}

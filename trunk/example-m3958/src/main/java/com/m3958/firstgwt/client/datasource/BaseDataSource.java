package com.m3958.firstgwt.client.datasource;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public abstract class BaseDataSource extends RestDataSource {
	
	protected Map<String, String> defaultParameters = new HashMap<String, String>();
	
	@Inject
	protected AppStatusService aservice;
	
	@Inject
	protected MyEventBus eventBus;
	
	protected DataSourceTextField getCreatorIdsField(){
		DataSourceTextField creatorIdsField = new DataSourceTextField(SmartParameterName.CREATORIDS);
		return creatorIdsField;
	}
	
	protected DataSourceIntegerField getIdField(){
		DataSourceIntegerField idField = new DataSourceIntegerField(CommonField.ID.getEname());
		idField.setPrimaryKey(true);
		idField.setHidden(true);
		return idField;
	}
	
	protected DataSourceIntegerField getVisibleIdField(){
		DataSourceIntegerField idField = new DataSourceIntegerField(CommonField.ID.getEname());
		idField.setPrimaryKey(true);
		idField.setHidden(false);
		return idField;
	}
	
	protected DataSourceIntegerField getParentIdHiddenField(){
		DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);
        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
		parentField.setHidden(true);
		return parentField;
	}
	
	protected DataSourceIntegerField getPerpageField(){
		DataSourceIntegerField idField = new DataSourceIntegerField(CommonField.PER_PAGE.getEname(),CommonField.PER_PAGE.getCname());
		return idField;
	}
	
	protected DataSourceBooleanField getAuditField(){
		DataSourceBooleanField auditField = new DataSourceBooleanField(CommonField.AUDIT.getEname(),CommonField.AUDIT.getCname());
		return auditField;
	}
	
	
	protected DataSourceIntegerField getPositionField(){
		DataSourceIntegerField positionField = new DataSourceIntegerField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
		return positionField;
	}
	
	protected DataSourceIntegerField getVersionField(){
		DataSourceIntegerField versionField = new DataSourceIntegerField("version");
		versionField.setHidden(true);
		return versionField;
	}

	protected DataSourceTextField getTagNamesField(){
		DataSourceTextField tagNamesField = new DataSourceTextField(CommonField.TAG_NAMES.getEname(),CommonField.TAG_NAMES.getCname());
		return tagNamesField;
	}

	
	protected DataSourceIntegerField getSiteIdField(){
		DataSourceIntegerField siteIdField = new DataSourceIntegerField(CommonField.SITE_ID.getEname(), CommonField.SITE_ID.getCname());
		siteIdField.setHidden(true);
		return siteIdField;
	}
	
	protected DataSourceDateTimeField getCreatedAtField(boolean isShow){
        DataSourceDateTimeField createdAtField = new DataSourceDateTimeField(CommonField.CREATED_AT.getEname());
        createdAtField.setHidden(isShow);
        createdAtField.setCanFilter(false);
        return createdAtField;
	}
	
	protected DataSourceTextField getSubFetchOperationTypeField(){
		DataSourceTextField f = new DataSourceTextField(SmartParameterName.SUB_OPERATION_TYPE);
		return f;
	}
	
	protected DataSourceTextField getTplNameField(){
		DataSourceTextField tplNameField = new DataSourceTextField(CommonField.TPL_NAME.getEname(),CommonField.TPL_NAME.getEname());
		return tplNameField;
	}
 
	protected DataSourceTextField getRelationPropertyNameField(){
		DataSourceTextField f = new DataSourceTextField(SmartParameterName.RELATION_PROPERTY_NAME);
		return f;
	}
	
	protected DataSourceTextField getRelationModelIdField(){
		DataSourceTextField f = new DataSourceTextField(SmartParameterName.RELATION_MODEL_ID);
		return f;
	}
	

	public BaseDataSource(){
		setSendMetaData(true);
        setDataURL(SmartConstants.SMART_CRUD_URL);
        setDataFormat(DSDataFormat.JSON);
        addParameters();
        setDefaultParams(defaultParameters);
	}
	

	protected void addParameters(){
		defaultParameters.put(SmartParameterName.MODEL_NAME, getModelClassName());
	}
	
	protected abstract String getModelClassName();
	
	
	
	protected String getListUrlStr(String paramName, String... fields){
		String s = "";
		for (int i = 0; i < fields.length; i++) {
			s += paramName + "=" + fields[i] + "&"; 
		}
		if(s.endsWith("&"))s = s.substring(0, s.length()-1);
		return s;
	}
	
	protected String getListUrlStr(String paramName,String[]...strArrays){
		String s = "";
		for (int i = 0; i < strArrays.length; i++) {
			String[] sa = strArrays[i];
			for(int j=0; j<sa.length;j++){
			s += paramName + "=" + sa[j] + "&";
			}
		}
		if(s.endsWith("&"))s = s.substring(0, s.length()-1);
		return s;
	}
}

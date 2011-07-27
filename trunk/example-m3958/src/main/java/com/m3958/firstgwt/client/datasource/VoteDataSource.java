package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.VoteField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class VoteDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Vote";
    
    @Inject
    public VoteDataSource(DsErrorHandler deh) {
    	setID("voteDS");
    	
    	setTitleField(CommonField.NAME.getEname());
    	
        DataSourceIntegerField parentField = new DataSourceIntegerField(SmartParameterName.PARENTID,SmartParameterName.PARENTID);

        parentField.setForeignKey(id + "." + CommonField.ID.getEname());
        parentField.setRequired(true);
        parentField.setRootValue(SmartConstants.NONE_EXIST_MODEL_ID);
        
        DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        DataSourceTextField chartTypeField = new DataSourceTextField(VoteField.CHART_TYPE.getEname(),VoteField.CHART_TYPE.getCname());
        DataSourceDateTimeField startField = new DataSourceDateTimeField(VoteField.START_DATE.getEname(),VoteField.START_DATE.getCname());
        DataSourceDateTimeField endField = new DataSourceDateTimeField(VoteField.END_DATE.getEname(),VoteField.END_DATE.getCname());        
        
        DataSourceIntegerField positionField = new DataSourceIntegerField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
        
        DataSourceIntegerField minSelectField = new DataSourceIntegerField(VoteField.MIN_SELECT.getEname(),VoteField.MIN_SELECT.getCname());
        
        DataSourceIntegerField maxSelectField = new DataSourceIntegerField(VoteField.MAX_SELECT.getEname(),VoteField.MAX_SELECT.getCname());
        
        DataSourceBooleanField hiddenField = new DataSourceBooleanField(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
        
         
        addHandleErrorHandler(deh);
        
        setFields(parentField,getSiteIdField(),hiddenField,getIdField(),getVersionField(),nameField,getCreatedAtField(false),getPositionField(),startField,endField,getTplNameField(),chartTypeField,minSelectField,maxSelectField,positionField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

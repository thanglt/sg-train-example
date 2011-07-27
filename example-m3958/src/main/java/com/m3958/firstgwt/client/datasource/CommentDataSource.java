package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommentField;
import com.m3958.firstgwt.client.types.CommonField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class CommentDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Comment";
    
    @Inject
    public CommentDataSource(DsErrorHandler deh) {
    	setID("commentDS");
    	
    	setTitleField(CommentField.MESSAGE.getEname());
    	
    	DataSourceEnumField obnameField = new DataSourceEnumField(CommonField.OBNAME.getEname(),CommonField.OBNAME.getCname());
    	obnameField.setRequired(true);
    	obnameField.setValueMap("article","xinjian","asset","page");
    	DataSourceTextField obidField = new DataSourceTextField(CommonField.OBID.getEname(),CommonField.OBID.getCname());
    	obidField.setRequired(true);
        
        DataSourceTextField nickNameField = new DataSourceTextField(CommentField.NICKNAME.getEname(),CommentField.NICKNAME.getCname());
        DataSourceTextField titleField = new DataSourceTextField(CommentField.TITLE.getEname(),CommentField.TITLE.getCname());
        DataSourceTextField emailField = new DataSourceTextField(CommentField.EMAIL.getEname(),CommentField.EMAIL.getCname());
        DataSourceTextField ipField = new DataSourceTextField(CommonField.IP.getEname(),CommonField.IP.getCname());
        
        DataSourceTextField messageField = new DataSourceTextField(CommentField.MESSAGE.getEname(),CommentField.MESSAGE.getCname());
        messageField.setLength(2000);
        messageField.setRequired(true);
        DataSourceIntegerField followNumField = new DataSourceIntegerField(CommentField.FOLLOWNUM.getEname(),CommentField.FOLLOWNUM.getCname());
        followNumField.setHidden(true);
        
        DataSourceBooleanField auditField = new DataSourceBooleanField(CommonField.AUDIT.getEname(),CommonField.AUDIT.getCname());
        
         
        addHandleErrorHandler(deh);
        
        setFields(obnameField,obidField,getParentIdHiddenField(),getSiteIdField(),auditField,getIdField(),getVersionField(),nickNameField,getCreatedAtField(true),titleField,emailField,ipField,followNumField,messageField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

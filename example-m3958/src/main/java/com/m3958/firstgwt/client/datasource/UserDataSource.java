package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.UserField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class UserDataSource extends BaseDataSource{
	
    public static String className = "com.m3958.firstgwt.model.User";
    
    @Inject
    public UserDataSource(DsErrorHandler deh) {
    	setID("userDS");
    	
    	
    	DataSourceIntegerField menuGroupField = new DataSourceIntegerField(UserField.MENULEVEL_ID.getEname());
        DataSourceTextField loginNameField = new DataSourceTextField(UserField.LOGIN_NAME.getEname());
        DataSourceTextField emailField = new DataSourceTextField(UserField.EMAIL.getEname());
        DataSourceTextField openIdField = new DataSourceTextField(UserField.OPENID.getEname());
        DataSourceTextField mobileField = new DataSourceTextField(UserField.MOBILE.getEname());
        DataSourceDateField birthDayField = new DataSourceDateField(UserField.BIRTHDAY.getEname());
        DataSourceBooleanField fcUserField = new DataSourceBooleanField(UserField.FCUSER.getEname());
        
        DataSourceTextField nicknameField = new DataSourceTextField(UserField.NICKNAME.getEname(),UserField.NICKNAME.getCname());
        DataSourceTextField avatorField = new DataSourceTextField(UserField.AVATAR.getEname(),UserField.AVATAR.getCname());
        
        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),loginNameField,emailField,fcUserField,openIdField,menuGroupField,mobileField,birthDayField,getCreatedAtField(false),getCreatorIdsField(),nicknameField,avatorField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}

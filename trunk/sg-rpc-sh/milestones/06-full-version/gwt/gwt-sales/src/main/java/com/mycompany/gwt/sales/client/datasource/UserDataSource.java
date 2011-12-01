package com.mycompany.gwt.sales.client.datasource;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.common.client.GenericGwtRpcDataSource;
import com.mycompany.gwt.sales.client.Sales;
import com.mycompany.gwt.sales.client.dto.UserDto;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.service.UserServiceRPC;
import com.mycompany.gwt.sales.client.service.UserServiceRPCAsync;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: User DataSource with i18n
 */
@Singleton
public class UserDataSource extends GenericGwtRpcDataSource<UserDto, Record, UserServiceRPCAsync> {

    @Override
    public List<DataSourceField> getDataSourceFields() {
        SalesConstants constants = GWT.create(SalesConstants.class);  //i18n constant

        List<DataSourceField> fields = new ArrayList<DataSourceField>();

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);
        fields.add(idField);

        DataSourceTextField username = new DataSourceTextField("username", constants.ds_title_username());
        username.setRequired(true);
        fields.add(username);

        DataSourceTextField password = new DataSourceTextField("password", constants.ds_title_password());
        password.setRequired(true);
        fields.add(password);

        DataSourceTextField amountField = new DataSourceTextField("email", constants.ds_title_email());
        amountField.setRequired(true);
        fields.add(amountField);

        DataSourceTextField name = new DataSourceTextField("name", constants.ds_title_name());
        fields.add(name);

        DataSourceBooleanField isAccountEnabled = new DataSourceBooleanField("isAccountEnabled", constants.ds_title_isAccountEnabled());
        fields.add(isAccountEnabled);

        DataSourceBooleanField isAccountLocked = new DataSourceBooleanField("isAccountLocked", constants.ds_title_isAccountLocked());
        fields.add(isAccountLocked);

        DataSourceBooleanField isAccountExpired = new DataSourceBooleanField("isAccountExpired", constants.ds_title_isAccountExpired());
        fields.add(isAccountExpired);

        DataSourceBooleanField isCredentialsExpired = new DataSourceBooleanField("isCredentialsExpired", constants.ds_title_isCredentialsExpired());
        fields.add(isCredentialsExpired);

        DataSourceIntegerField loginFailureCount = new DataSourceIntegerField("loginFailureCount" , constants.ds_title_loginFailureCount());
        fields.add(loginFailureCount);

        DataSourceDateField lockedDate = new DataSourceDateField("lockedDate" , constants.ds_title_lockedDate());
        fields.add(lockedDate);

        return fields;
    }

    @Override
    public void copyValues(Record from, UserDto to) {
        to.setId(from.getAttributeAsString("id"));
        to.setUsername(from.getAttributeAsString("username"));
        to.setPassword(from.getAttributeAsString("password"));
        to.setEmail(from.getAttributeAsString("email"));
        to.setName(from.getAttributeAsString("name"));
        to.setAccountEnabled(from.getAttributeAsBoolean("isAccountEnabled"));
        to.setAccountLocked(from.getAttributeAsBoolean("isAccountLocked"));
        to.setAccountExpired(from.getAttributeAsBoolean("isAccountExpired"));
        to.setCredentialsExpired(from.getAttributeAsBoolean("isCredentialsExpired"));
        to.setLoginFailureCount(from.getAttributeAsInt("loginFailureCount"));
        to.setLockedDate(from.getAttributeAsDate("lockedDate"));
    }

    @Override
    public void copyValues(UserDto from, Record to) {
        to.setAttribute("id" , from.getId());
        to.setAttribute("username" , from.getUsername());
        to.setAttribute("password" , from.getPassword());
        to.setAttribute("email" , from.getEmail());
        to.setAttribute("name" , from.getName());
        to.setAttribute("isAccountEnabled" , from.getAccountEnabled());
        to.setAttribute("isAccountLocked" , from.getAccountLocked());
        to.setAttribute("isAccountExpired" , from.getAccountExpired());
        to.setAttribute("isCredentialsExpired" , from.getCredentialsExpired());
        to.setAttribute("loginFailureCount" , from.getLoginFailureCount());
        to.setAttribute("lockedDate" , from.getLockedDate());
    }

    @Override
    public UserServiceRPCAsync getServiceAsync() {
        return GWT.create(UserServiceRPC.class);
    }

    @Override
    public Record getNewRecordInstance() {
        return new ListGridRecord();
    }

    @Override
    public UserDto getNewDataObjectInstance() {
        return new UserDto();
    }
}

package com.mycompany.gwt.sales.client.datasource;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;
import com.mycompany.gwt.common.client.GenericGwtRpcDataSource;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.service.RoleServiceRPC;
import com.mycompany.gwt.sales.client.service.RoleServiceRPCAsync;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: User DataSource with i18n
 */
@Singleton
public class RoleDataSource extends GenericGwtRpcDataSource<RoleDto, Record, RoleServiceRPCAsync> {

    @Override
    public List<DataSourceField> getDataSourceFields() {
        SalesConstants constants = GWT.create(SalesConstants.class);  //i18n constant

        List<DataSourceField> fields = new ArrayList<DataSourceField>();

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);
        fields.add(idField);

        DataSourceTextField name = new DataSourceTextField("name", constants.ds_role_name());
        name.setRequired(true);
        fields.add(name);

        DataSourceTextField value = new DataSourceTextField("value", constants.ds_role_value());
        value.setRequired(true);
        fields.add(value);

        DataSourceBooleanField isSystem = new DataSourceBooleanField("isSystem", constants.ds_role_isSystem());
        isSystem.setRequired(true);
        fields.add(isSystem);

        DataSourceTextField description = new DataSourceTextField("description", constants.ds_role_description());
        fields.add(description);

        DataSourceTextField projectIdField = new DataSourceTextField("projectId", constants.ds_role_projectId());
        projectIdField.setHidden(true);
        fields.add(projectIdField);

        return fields;
    }

    @Override
    public void copyValues(Record from, RoleDto to) {
        to.setId(from.getAttributeAsString("id"));
        to.setName(from.getAttributeAsString("name"));
        to.setValue(from.getAttributeAsString("value"));
        to.setSystem(from.getAttributeAsBoolean("isSystem"));
        to.setDescription(from.getAttributeAsString("description"));
        to.setProjectId(from.getAttributeAsString("projectId"));
    }

    @Override
    public void copyValues(RoleDto from, Record to) {
        to.setAttribute("id", from.getId());
        to.setAttribute("name", from.getName());
        to.setAttribute("value", from.getValue());
        to.setAttribute("isSystem", from.getSystem());
        to.setAttribute("description", from.getDescription());
        to.setAttribute("projectId" , from.getProjectId());
    }

    @Override
    public RoleServiceRPCAsync getServiceAsync() {
        return GWT.create(RoleServiceRPC.class);
    }

    @Override
    public Record getNewRecordInstance() {
        return new ListGridRecord();
    }

    @Override
    public RoleDto getNewDataObjectInstance() {
        return new RoleDto();
    }
}

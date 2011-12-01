package com.mycompany.gwt.sales.client.datasource;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;
import com.mycompany.gwt.common.client.GenericGwtRpcDataSource;
import com.mycompany.gwt.sales.client.dto.MenuNodeDto;
import com.mycompany.gwt.sales.client.service.MenuNodeServiceRPC;
import com.mycompany.gwt.sales.client.service.MenuNodeServiceRPCAsync;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class MenuNodeDataSource extends GenericGwtRpcDataSource<MenuNodeDto, Record, MenuNodeServiceRPCAsync> {
    @Override
    public List<DataSourceField> getDataSourceFields() {
        List<DataSourceField> fields = new ArrayList<DataSourceField>();

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);
        fields.add(idField);

        DataSourceTextField nameField = new DataSourceTextField("name", "Name");
        nameField.setRequired(true);
        fields.add(nameField);

        DataSourceTextField locationField = new DataSourceTextField("parentId", "ParentId");
        locationField.setRequired(true);
        fields.add(locationField);

        DataSourceTextField categoryField = new DataSourceTextField("category", "Category");
        fields.add(categoryField);

        DataSourceTextField isFolder = new DataSourceTextField("isFolder", "IsFolder");
        fields.add(isFolder);

        DataSourceTextField icon = new DataSourceTextField("icon", "Icon");
        fields.add(icon);

        DataSourceField viewId = new DataSourceTextField("viewId" , "View Id");
        viewId.setHidden(true);
        fields.add(viewId);

        return fields;
    }

    @Override
    public void copyValues(Record from, MenuNodeDto to) {
        to.setId(from.getAttributeAsString("id"));
        to.setName(from.getAttribute("name"));
        to.setParentId(from.getAttribute("parentId"));
        to.setCategory(from.getAttribute("category"));
        to.setFolder(from.getAttributeAsBoolean("isFolder"));
        to.setIcon(from.getAttribute("icon"));
        to.setViewId(from.getAttribute("viewId"));
    }

    @Override
    public void copyValues(MenuNodeDto from, Record to) {
        to.setAttribute("id", from.getId());
        to.setAttribute("name", from.getName());
        to.setAttribute("parentId", from.getParentId());
        to.setAttribute("category", from.getCategory());
        to.setAttribute("isFolder", from.isFolder());
        to.setAttribute("icon", from.getIcon());
        to.setAttribute("viewId" , from.getViewId());
    }

    @Override
    public MenuNodeServiceRPCAsync getServiceAsync() {
        return GWT.create(MenuNodeServiceRPC.class);
    }

    @Override
    public Record getNewRecordInstance() {
        return new TreeNode();
    }

    @Override
    public MenuNodeDto getNewDataObjectInstance() {
        return new MenuNodeDto();
    }
}

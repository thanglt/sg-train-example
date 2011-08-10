package mytest.client.datasource;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import mytest.client.lib.GenericGwtRpcDataSource;
import mytest.client.service.MenuNodeServiceRPC;
import mytest.client.service.MenuNodeServiceRPCAsync;
import mytest.shared.MenuNodeDto;

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
    }

    @Override
    public void copyValues(MenuNodeDto from, Record to) {
        to.setAttribute("id", from.getId());
        to.setAttribute("name", from.getName());
        to.setAttribute("parentId", from.getParentId());
        to.setAttribute("category", from.getCategory());
        to.setAttribute("isFolder", from.isFolder());
        to.setAttribute("icon", from.getIcon());

    }

    @Override
    public MenuNodeServiceRPCAsync getServiceAsync() {
        return GWT.create(MenuNodeServiceRPC.class);
    }

    @Override
    public Record getNewRecordInstance() {
        return new ListGridRecord();
    }

    @Override
    public MenuNodeDto getNewDataObjectInstance() {
        return new MenuNodeDto();
    }
}

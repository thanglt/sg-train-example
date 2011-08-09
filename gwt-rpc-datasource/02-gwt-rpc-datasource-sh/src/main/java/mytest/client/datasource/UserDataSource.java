package mytest.client.datasource;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import mytest.client.lib.GenericGwtRpcDataSource;
import mytest.client.service.UserServiceRPC;
import mytest.client.service.UserServiceRPCAsync;
import mytest.shared.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource extends GenericGwtRpcDataSource<UserDto, Record, UserServiceRPCAsync> {

    private static UserDataSource instance;

    // forces to use the singleton through getInstance();
    private UserDataSource() {
    }

    public static UserDataSource getInstance() {
        if (instance == null) {
            instance = new UserDataSource();
        }
        return (instance);
    }

    @Override
    public void copyValues(Record from, UserDto to) {
        to.setId(from.getAttributeAsString("id"));
        to.setName(from.getAttribute("name"));
        to.setLocation(from.getAttribute("location"));
    }

    @Override
    public void copyValues(UserDto from, Record to) {
        to.setAttribute("id", from.getId());
        to.setAttribute("name", from.getName());
        to.setAttribute("location", from.getLocation());
    }

    @Override
    public List<DataSourceField> getDataSourceFields() {

        List<DataSourceField> fields = new ArrayList<DataSourceField>();

        DataSourceIntegerField idField = new DataSourceIntegerField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);
        fields.add(idField);

        DataSourceTextField nameField = new DataSourceTextField("name", "Name");
        nameField.setRequired(true);
        fields.add(nameField);

        DataSourceTextField locationField = new DataSourceTextField("location", "Location");
        locationField.setRequired(true);
        fields.add(locationField);

        return fields;
    }

    @Override
    public UserDto getNewDataObjectInstance() {
        return new UserDto();
    }

    @Override
    public ListGridRecord getNewRecordInstance() {
        return new ListGridRecord();
    }

    @Override
    public UserServiceRPCAsync getServiceAsync() {
        return GWT.create(UserServiceRPC.class);
    }

}

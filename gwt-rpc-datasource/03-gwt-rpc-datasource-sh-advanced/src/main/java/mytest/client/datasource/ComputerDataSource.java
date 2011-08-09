package mytest.client.datasource;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import mytest.client.lib.GenericGwtRpcDataSource;
import mytest.client.service.ComputerServiceRPC;
import mytest.client.service.ComputerServiceRPCAsync;
import mytest.shared.ComputerDto;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ComputerDataSource extends GenericGwtRpcDataSource<ComputerDto, Record, ComputerServiceRPCAsync> {

    private static ComputerDataSource instance;

    // forces to use the singleton through getInstance();
    /*private ComputerDataSource() {
        setShowPrompt(true);
    }

    public static ComputerDataSource getInstance() {
        if (instance == null) {
            instance = new ComputerDataSource();
        }
        return (instance);
    }*/

    @Override
    public List<DataSourceField> getDataSourceFields() {
        List<DataSourceField> fields = new ArrayList<DataSourceField>();

        DataSourceIntegerField idField = new DataSourceIntegerField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);
        fields.add(idField);

        DataSourceTextField nameField = new DataSourceTextField("type", "Type");
        nameField.setRequired(true);
        fields.add(nameField);

        DataSourceTextField locationField = new DataSourceTextField("code", "Code");
        locationField.setRequired(true);
        fields.add(locationField);

        return fields;
    }

    @Override
    public void copyValues(Record from, ComputerDto to) {
        to.setId(from.getAttributeAsString("id"));
        to.setType(from.getAttribute("type"));
        to.setCode(from.getAttribute("code"));
    }

    @Override
    public void copyValues(ComputerDto from, Record to) {
        to.setAttribute("id", from.getId());
        to.setAttribute("type", from.getType());
        to.setAttribute("code", from.getCode());
    }

    @Override
    public ComputerServiceRPCAsync getServiceAsync() {
        return GWT.create(ComputerServiceRPC.class);
    }

    @Override
    public ListGridRecord getNewRecordInstance() {
        return new ListGridRecord();
    }

    @Override
    public ComputerDto getNewDataObjectInstance() {
        return new ComputerDto();
    }
}

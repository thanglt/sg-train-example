package mytest.client;

import java.util.ArrayList;
import java.util.List;

import mytest.client.lib.GenericGwtRpcDataSource;
import mytest.shared.YourDataObject;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class YourDataSource
		extends
		GenericGwtRpcDataSource<YourDataObject, ListGridRecord, YourServiceAsync> {

	private static YourDataSource instance;

	// forces to use the singleton through getInstance();
	private YourDataSource() {
	};

	public static YourDataSource getInstance() {
		if (instance == null) {
			instance = new YourDataSource();
		}
		return (instance);
	}

	@Override
	public void copyValues(ListGridRecord from, YourDataObject to) {
		to.setId(from.getAttributeAsInt("id"));
		to.setName(from.getAttribute("name"));
		to.setLocation(from.getAttribute("location"));
	}

	@Override
	public void copyValues(YourDataObject from, ListGridRecord to) {
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
	public YourDataObject getNewDataObjectInstance() {
		return new YourDataObject();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public YourServiceAsync getServiceAsync() {
		return GWT.create(YourService.class);
	}

}

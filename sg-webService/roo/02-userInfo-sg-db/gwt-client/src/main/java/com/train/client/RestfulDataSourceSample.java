package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.*;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-11-17
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class RestfulDataSourceSample implements EntryPoint {

    private static final int STATUS_CODE_OK = 200;

    public void onModuleLoad() {

        VLayout layout = new VLayout(15);
        layout.setAutoHeight();

        //overrides here are for illustration purposes only
        RestDataSource accountDS = new RestDataSource() {
            @Override
            protected Object transformRequest(DSRequest dsRequest) {
                return super.transformRequest(dsRequest);
            }

            @Override
            protected void transformResponse(DSResponse response, DSRequest request, Object data) {
                super.transformResponse(response, request, data);
            }
        };
        DataSourceTextField countryCode = new DataSourceTextField("countryCode", "Code");
        OperationBinding fetch = new OperationBinding();
        fetch.setOperationType(DSOperationType.FETCH);
        fetch.setDataProtocol(DSProtocol.POSTMESSAGE);
        OperationBinding add = new OperationBinding();
        add.setOperationType(DSOperationType.ADD);
        add.setDataProtocol(DSProtocol.POSTMESSAGE);
        OperationBinding update = new OperationBinding();
        update.setOperationType(DSOperationType.UPDATE);
        update.setDataProtocol(DSProtocol.POSTMESSAGE);
        OperationBinding remove = new OperationBinding();
        remove.setOperationType(DSOperationType.REMOVE);
        remove.setDataProtocol(DSProtocol.POSTMESSAGE);
        accountDS.setOperationBindings(fetch, add, update, remove);

        accountDS.setDataFormat(DSDataFormat.JSON);

        countryCode.setPrimaryKey(true);
        countryCode.setCanEdit(false);

        DataSourceTextField countryName = new DataSourceTextField("countryName", "Country");
        DataSourceTextField capital = new DataSourceTextField("capital", "Capital");
        accountDS.setFields(countryCode, countryName, capital);
        accountDS.setFetchDataURL("data/dataIntegration/xml/responses/country_fetch_rest.xml");
        accountDS.setAddDataURL("data/dataIntegration/xml/responses/country_add_rest.xml");
        accountDS.setUpdateDataURL("data/dataIntegration/xml/responses/country_update_rest.xml");
        accountDS.setRemoveDataURL("data/dataIntegration/xml/responses/country_remove_rest.xml");

        final ListGrid countryGrid = new ListGrid();
        countryGrid.setWidth(500);
        countryGrid.setHeight(224);
        countryGrid.setDataSource(accountDS);
        countryGrid.setEmptyCellValue("--");

        ListGridField codeField = new ListGridField("countryCode");
        ListGridField nameField = new ListGridField("countryName");
        ListGridField capitalField = new ListGridField("capital");
        ListGridField continentField = new ListGridField("continent", "Continent");
        countryGrid.setFields(codeField, nameField, capitalField, continentField);
        countryGrid.setSortField(0);
        countryGrid.setDataPageSize(50);
        countryGrid.setAutoFetchData(true);

        layout.addMember(countryGrid);

        HLayout hLayout = new HLayout(15);

        final IButton addButton = new IButton("Add new Country");
        addButton.setWidth(150);
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
//                countryGrid.addData(new CountryRecord("A1", "New Value", "New Value", "New Value"));
//                addButton.disable();
            }
        });
        hLayout.addMember(addButton);

        final IButton updateButton = new IButton("Update Country (US)");
        updateButton.setWidth(150);
        updateButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.PUT, "customerRecord.xml");
                try {
                    requestBuilder.setTimeoutMillis(5000);
                    String data = "id=1&username=testUsername";
                    requestBuilder.sendRequest(data, new RequestCallback() {
                        public void onResponseReceived(Request request, Response response) {
                            if (STATUS_CODE_OK == response.getStatusCode()) {
                                SC.say(response.getText());
                            } else {
                                SC.warn("HTTP ERROR:" +response.getStatusCode());
                            }
                        }
                        public void onError(Request request, Throwable exception) {
                            if (exception instanceof RequestTimeoutException) {
                                SC.warn("Request timeout, please try again...");
                            } else {
                                SC.warn(exception.getMessage());
                            }
                        }
                    });
                } catch (RequestException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        hLayout.addMember(updateButton);

        final IButton removeButton = new IButton("Remove Country (UK)");
        removeButton.setWidth(150);
        removeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
//                CountryRecord record = new CountryRecord();
//                record.setCountryCode("UK");
//                countryGrid.removeData(record);
//                removeButton.disable();
            }
        });
        hLayout.addMember(removeButton);
        layout.addMember(hLayout);
        layout.draw();
    }
}

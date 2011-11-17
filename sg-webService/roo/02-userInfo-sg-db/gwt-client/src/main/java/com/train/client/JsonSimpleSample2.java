package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.*;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class JsonSimpleSample2 implements EntryPoint {

    private static final int STATUS_CODE_OK = 200;

    public void onModuleLoad() {

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();

        final DataSource accountDS = new DataSource();
        accountDS.setDataFormat(DSDataFormat.XML);
        accountDS.setDataURL("/accounts.xml");
        accountDS.setRecordXPath("//account");

        DataSourceTextField idField = new DataSourceTextField("id", "Id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField nameField = new DataSourceTextField("username", "User Name");

        DataSourceTextField passwordfield = new DataSourceTextField("password", "Password");

        DataSourceTextField disabledField = new DataSourceTextField("isDisabled", "Is Disabled");

        accountDS.setFields(idField, nameField, passwordfield, disabledField);

        final ListGrid grid = new ListGrid();
        grid.setDataSource(accountDS);
        grid.setWidth100();
        grid.setHeight(300);
//        grid.setAutoFetchData(true);

        //DynamicForm
        final DynamicForm form = new DynamicForm();

        TextItem idItem = new TextItem("id", "Id");
        idItem.setDisabled(true);

        TextItem usernameItem = new TextItem("username", "User Name");
        TextItem passwordItem = new TextItem("password", "Password");
        TextItem disabledItem = new TextItem("isDisabled", "Is Disabled");

        form.setItems(idItem, usernameItem, passwordItem, disabledItem);

        //buttons
        final IButton btnFetch = new IButton("Fetch");
        btnFetch.setWidth(100);

        final IButton btnAdd = new IButton("Add");
        btnAdd.setWidth(100);

        final IButton btnReset = new IButton("Reset");
        btnReset.setWidth(100);

        final IButton btnUpdate = new IButton("Update");
        btnUpdate.setWidth(100);

        final IButton btnRemove = new IButton("Remove");
        btnRemove.setWidth(100);

        layout.addMember(grid);
        layout.addMember(form);
        layout.addMember(btnFetch);
        layout.addMember(btnAdd);
        layout.addMember(btnReset);
        layout.addMember(btnUpdate);
        layout.addMember(btnRemove);

        //event
        grid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent recordClickEvent) {
                form.editRecord(grid.getSelectedRecord());
                btnAdd.disable();
            }
        });

        //Fetch
        btnFetch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                grid.invalidateCache();
                grid.fetchData();
            }
        });

        //Add
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
//                accountDS.performCustomOperation();
//                accountDS.addData();
            }
        });

        btnReset.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                form.clearValues();
                btnAdd.enable();
            }
        });

        //Update
        btnUpdate.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.PUT, "/accounts");
                try {
                    requestBuilder.setTimeoutMillis(5000);
                    String data = "id=1&username=gwtUsername&password=gwtPass";
                    requestBuilder.sendRequest(data, new RequestCallback() {
                        public void onResponseReceived(Request request, Response response) {
                            if (STATUS_CODE_OK == response.getStatusCode()) {
//                                SC.say(response.getText());
                                grid.invalidateCache();
                                grid.fetchData();
                            } else {
                                SC.warn("HTTP ERROR:" + response.getStatusCode());
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

        layout.draw();
    }
}

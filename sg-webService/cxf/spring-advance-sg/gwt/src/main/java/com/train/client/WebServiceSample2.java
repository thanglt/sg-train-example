package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.data.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class WebServiceSample2 implements EntryPoint {

    private final String wsdlURL = "services/UserService?wsdl";
    private final String namespaceURL = "http://webservice.server.sample.webservice.mycompany.com/";
    private final String wsFetchOp = "GetList";
    private final String recordName = "User";
    private WebService service;
    private DynamicForm form;
    private ListGrid grid;

    public void onModuleLoad() {

        VLayout vLayout = new VLayout();
        vLayout.setWidth100();
        vLayout.setHeight100();

        form = new DynamicForm();
        TextItem nameItem = new TextItem("name", "Name");
        TextItem emailItem = new TextItem("email", "Email");
        ButtonItem queryItem = new ButtonItem("", "Fetch all");
        queryItem.setWidth(100);
        ButtonItem createItem = new ButtonItem("", "Create");
        createItem.setWidth(100);
        ButtonItem updateItem = new ButtonItem("", "Update");
        updateItem.setWidth(100);
        ButtonItem deleteItem = new ButtonItem("", "Delete");
        deleteItem.setWidth(100);
        form.setItems(nameItem, emailItem, queryItem, createItem, updateItem, deleteItem);
        form.setDisabled(true);

        grid = new ListGrid();
        grid.setWidth100();
        grid.setHeight(300);
        grid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent recordClickEvent) {
                form.editRecord(grid.getSelectedRecord());
            }
        });

        IButton btnGetWsdl = new IButton("get wdsl");
        btnGetWsdl.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                XMLTools.loadWSDL(wsdlURL, new WSDLLoadCallback() {
                    public void execute(WebService webService) {
                        SC.showPrompt("Loading WSDL from: " + wsdlURL);
                        //init
                        service = webService;
                        form.setDisabled(false);

                        //listgrid
                        DataSource userDS = new DataSource();
                        userDS.setServiceNamespace(namespaceURL);
                        userDS.setRecordName(recordName);

                        OperationBinding fetchOp = new OperationBinding(DSOperationType.FETCH, wsdlURL);
                        fetchOp.setWsOperation(wsFetchOp);
                        fetchOp.setRecordName(recordName);

                        OperationBinding createOp = new OperationBinding(DSOperationType.ADD , wsdlURL);
//                        createOp.setWsOperation();

                        userDS.setOperationBindings(fetchOp);

                        userDS.setFields(
                                new DataSourceField("id", FieldType.TEXT)
                                , new DataSourceField("name", FieldType.TEXT)
                                , new DataSourceField("email", FieldType.TEXT)
                        );

                        grid.setDataSource(userDS);
                        SC.clearPrompt();
                    }
                });
            }
        });

        //query
        queryItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent clickEvent) {
                grid.fetchData();
            }
        });

        //Create
        createItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent clickEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        vLayout.addMember(btnGetWsdl);
        vLayout.addMember(grid);
        vLayout.addMember(form);
        vLayout.draw();

    }

}


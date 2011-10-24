package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.RPCResponse;
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

import java.util.HashMap;
import java.util.Map;

public class WebServiceSample2 implements EntryPoint {

    private final String wsdlURL = "services/UserService?wsdl";
    private final String namespaceURL = "http://webservice.server.sample.webservice.mycompany.com/";
    private final String wsFetchOp = "GetList";
    private final String wsAddOp = "Create";
    private final String recordName = "User";
    private WebService service;
    private DynamicForm form;
    private ListGrid grid;
    private DataSource userDS;

    public void onModuleLoad() {

        VLayout vLayout = new VLayout();
        vLayout.setWidth100();
        vLayout.setHeight100();

        form = new DynamicForm();
        TextItem nameItem = new TextItem("name", "Name");
        TextItem emailItem = new TextItem("email", "Email");

        final IButton btnFetch = new IButton("Fetch");
        btnFetch.setWidth(100);
        btnFetch.setDisabled(true);

        final IButton btnAdd = new IButton("Add");
        btnAdd.setWidth(100);
        btnAdd.setDisabled(true);

        final IButton btnUpdate = new IButton("Update");
        btnUpdate.setWidth(100);
        btnUpdate.setDisabled(true);

        final IButton btnRemove = new IButton("Remove");
        btnRemove.setWidth(100);
        btnRemove.setDisabled(true);

        form.setItems(nameItem, emailItem );
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

                        //listgrid
                        userDS = new DataSource();
                        userDS.setServiceNamespace(namespaceURL);
                        userDS.setRecordName(recordName);

                        OperationBinding fetchOp = new OperationBinding(DSOperationType.FETCH, wsdlURL);
                        fetchOp.setWsOperation(wsFetchOp);
                        fetchOp.setRecordName(recordName);

                        OperationBinding createOp = new OperationBinding(DSOperationType.ADD , wsdlURL);
                        createOp.setWsOperation(wsAddOp);

                        userDS.setOperationBindings(fetchOp);

                        userDS.setFields(
                                new DataSourceField("id", FieldType.TEXT)
                                , new DataSourceField("name", FieldType.TEXT)
                                , new DataSourceField("email", FieldType.TEXT)
                                , new DataSourceField("lastUpdateDate" , FieldType.TIME)
                        );

                        grid.setDataSource(userDS);
                        form.setDataSource(userDS);
                        form.setDisabled(false);
                        btnFetch.setDisabled(false);
                        btnAdd.setDisabled(false);
                        btnUpdate.setDisabled(false);
                        btnRemove.setDisabled(false);
                        SC.clearPrompt();
                    }
                });
            }
        });

        //query
        btnFetch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                grid.fetchData();
            }
        });

        //Create
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                Map map = new HashMap();
                map.put("Name" , "test-name");
                map.put("Email" , "test-email");
                service.callOperation(wsAddOp, map, "success" , new WebServiceCallback(){
                    public void execute(Object[] data, JavaScriptObject xmlDoc, RPCResponse rpcResponse, JavaScriptObject wsRequest) {
                        //To change body of implemented methods use File | Settings | File Templates.
                        int i = rpcResponse.getStatus();
                        SC.say("status : " + i);
                    }
                });
            }
        });

        vLayout.addMember(btnGetWsdl);
        vLayout.addMember(grid);
        vLayout.addMember(form);
        vLayout.addMember(btnFetch);
        vLayout.addMember(btnAdd);
        vLayout.addMember(btnUpdate);
        vLayout.addMember(btnRemove);
        vLayout.draw();

    }

}


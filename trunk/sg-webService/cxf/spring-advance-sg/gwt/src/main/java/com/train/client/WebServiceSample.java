package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSOHelper;
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
import com.train.client.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class WebServiceSample implements EntryPoint {

    private final String wsdlURL = "services/UserService?wsdl";
    private final String namespaceURL = "http://webservice.server.sample.webservice.mycompany.com/";
    private final String wsFetchOp = "GetList";
    private final String wsAddOp = "Create";
    private final String wsUpdateOp = "Update";
    private final String wsRemoveOp = "Delete";
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
        TextItem nameItem = new TextItem("Name", "Name");
        TextItem emailItem = new TextItem("Email", "Email");

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

        form.setItems(nameItem, emailItem);
        form.setDisabled(true);


        grid = new ListGrid();
        grid.setWidth100();
        grid.setHeight(300);
        grid.setAlign(Alignment.CENTER);
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

                        OperationBinding createOp = new OperationBinding(DSOperationType.ADD, wsdlURL);
                        createOp.setWsOperation(wsAddOp);

                        userDS.setOperationBindings(fetchOp);

                        DataSourceField idField = new DataSourceField("id", FieldType.TEXT);
                        idField.setHidden(true);

                        DataSourceField timeField = new DataSourceField("lastUpdateDate", FieldType.TIME);

                        userDS.setFields(
                                idField
                                , new DataSourceField("name", FieldType.TEXT)
                                , new DataSourceField("email", FieldType.TEXT)
                                , timeField
                        );

                        grid.setDataSource(userDS);
                        form.setDataSource(userDS);
                        form.setDisabled(false);
                        btnFetch.setDisabled(false);
                        btnAdd.setDisabled(false);
                        btnUpdate.setDisabled(false);
                        btnRemove.setDisabled(false);
                        SC.clearPrompt();
                        grid.fetchData();
                    }
                });
            }
        });


        //query
        btnFetch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                freshListGrid();
            }
        });

        //Create
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String name = form.getValueAsString("name");
                String email = form.getValueAsString("email");
                Map data = new HashMap();
                if (StringUtils.isNotBlank(name)) {
                    data.put("Name", name);
                }
                if (StringUtils.isNotBlank(email)) {
                    data.put("Email", email);
                }
                service.callOperation(wsAddOp, data, "//OperationOutputType", new WebServiceCallback() {
                    public void execute(Object[] data, JavaScriptObject xmlDoc, RPCResponse rpcResponse, JavaScriptObject wsRequest) {
                        Map map = JSOHelper.convertToMap((JavaScriptObject) data[0]);
                        String success = (String) map.get("success");
                        if ("true".equals(success)) {
                            freshListGrid();
                        } else {
                            String reason = (String) map.get("responseText");
                            SC.warn("Operation failed : " + reason);
                        }
                    }
                });
            }
        });

        //Update
        btnUpdate.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String id = form.getValueAsString("id");
                String name = form.getValueAsString("name");
                String email = form.getValueAsString("email");
                if (StringUtils.isBlank(id)) {
                    return;
                }
                Map data = new HashMap();
                data.put("Id", id);
                if (StringUtils.isNotBlank(name)) {
                    data.put("Name", name);
                }
                if (StringUtils.isNotBlank(email)) {
                    data.put("Email", email);
                }
                service.callOperation(wsUpdateOp, data, "success", new WebServiceCallback() {
                    public void execute(Object[] data, JavaScriptObject xmlDoc, RPCResponse rpcResponse, JavaScriptObject wsRequest) {
                        int i = rpcResponse.getStatus();
                        freshListGrid();
                    }
                });
            }
        });

        //Remove
        btnRemove.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String id = form.getValueAsString("id");
                Map data = new HashMap();
                if (StringUtils.isBlank(id)) {
                    return;
                }
                data.put("Id", id);
                service.callOperation(wsRemoveOp, data, "success", new WebServiceCallback() {
                    public void execute(Object[] data, JavaScriptObject xmlDoc, RPCResponse rpcResponse, JavaScriptObject wsRequest) {
                        int i = rpcResponse.getStatus();
                        freshListGrid();
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

    private void freshListGrid() {
        grid.invalidateCache();
        grid.fetchData();
    }

}


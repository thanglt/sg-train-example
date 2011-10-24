package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.data.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class WebServiceSample implements EntryPoint {

    public void onModuleLoad() {

        final Canvas canvas = new Canvas();
        canvas.setWidth100();
        canvas.setHeight100();

//        final String wsdlURL = "http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL";
        final String wsdlURL = "services/UserService?wsdl";
        final String namespaceURL = "http://webservice.server.sample.webservice.mycompany.com/";
        final String wsOperation = "GetList";
        final String recordName = "User";

        SC.showPrompt("Loading WSDL from: " + wsdlURL);
        XMLTools.loadWSDL(wsdlURL, new WSDLLoadCallback() {
            public void execute(WebService service) {
                if (service == null) {
                    SC.warn("WSDL not currently available from CDYNE (tried " + wsdlURL + ")", new BooleanCallback() {
                        public void execute(Boolean value) {
                        }
                    });
                    return;
                }

//                DataSource inputDS = service.getInputDS(wsOperation);

//                XmlNamespaces ns = new XmlNamespaces();
//                ns.addNamespace("ns2", namespaceURL);

                DataSource resultDS = new DataSource();
                resultDS.setServiceNamespace(namespaceURL);
//                resultDS.setXmlNamespaces(ns);
                resultDS.setRecordName(recordName);

                OperationBinding opb = new OperationBinding(DSOperationType.FETCH, wsdlURL);
//                opb.setXmlNamespaces(ns);
                opb.setWsOperation(wsOperation);
                opb.setRecordName(recordName);
                resultDS.setOperationBindings(opb);

                resultDS.setFields(
                        new DataSourceField("id", FieldType.TEXT)
                        , new DataSourceField("name", FieldType.TEXT)
                        , new DataSourceField("email", FieldType.TEXT)
                );

                VLayout layout = new VLayout(20);
                layout.setWidth100();
                layout.setHeight100();
                layout.setLayoutMargin(40);

//                final DynamicForm searchForm = new DynamicForm();
//                searchForm.setNumCols(4);
//                searchForm.setWidth(500);
//                searchForm.setDataSource(inputDS);

                final ListGrid listGrid = new ListGrid();
                listGrid.setWidth100();
                listGrid.setDataSource(resultDS);

                IButton searchButton = new IButton("Search");
                searchButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        listGrid.fetchData(new Criteria(), new DSCallback() {
                            public void execute(DSResponse response, Object rawData, DSRequest request) {
                                int status = response.getStatus();
                                Record[] data = response.getData();
                                SC.say("data length : " + data.length);
                            }
                        });
                    }
                });

//                layout.addMember(searchForm);
                layout.addMember(searchButton);
                layout.addMember(listGrid);
                canvas.addChild(layout);

                SC.clearPrompt();
            }
        });

        canvas.draw();
    }

}


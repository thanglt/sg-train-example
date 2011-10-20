package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.WSDLLoadCallback;
import com.smartgwt.client.data.WebService;
import com.smartgwt.client.data.WebServiceCallback;
import com.smartgwt.client.data.XMLTools;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import java.util.LinkedHashMap;
import java.util.Map;

public class myGwt implements EntryPoint {

    private final String wsdl = "/services/UserService?wsdl";
//    private final String wsdl = "http://localhost:8082/services/UserService?wsdl";

    private WebService service;

    public void onModuleLoad() {

        //name input
        IButton btn = new IButton("get wsdl/wadl");

        final IButton btn2 = new IButton("get data");
        btn2.setDisabled(true);

        final IButton btn3 = new IButton("get data 2");
        btn3.setDisabled(true);

        final Label label = new Label("...");

        btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                XMLTools.loadWSDL(wsdl, new WSDLLoadCallback() {
                    public void execute(WebService webService) {
                        service = webService;
                        btn2.setDisabled(false);
                        btn3.setDisabled(false);
                    }
                });
            }
        });

        btn2.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Map data = new LinkedHashMap();
                service.callOperation("getList", data, "", new WebServiceCallback() {
                    public void execute(Object[] data, JavaScriptObject xmlDoc, RPCResponse rpcResponse, JavaScriptObject wsRequest) {
                        System.out.println(data.length);
                        String s = (String) data[0];
                        label.setContents(s);
                    }
                });
            }
        });

        btn3.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Map data = new LinkedHashMap();
                service.callOperation("getList", data, "getListResponse", new WebServiceCallback() {
                    public void execute(Object[] data, JavaScriptObject xmlDoc, RPCResponse rpcResponse, JavaScriptObject wsRequest) {
                        System.out.println(data.length);
                        String s = (String) data[0];
                        label.setContents(s);
                    }
                });
            }
        });

        VLayout lay = new VLayout();
        lay.setWidth100();
        lay.setHeight100();
        lay.addMember(btn);
        lay.addMember(btn2);
        lay.addMember(btn3);
        lay.addMember(label);
        lay.draw();
    }
}

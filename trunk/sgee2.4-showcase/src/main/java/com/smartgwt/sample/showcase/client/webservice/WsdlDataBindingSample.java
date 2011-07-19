/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.client.webservice;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.XMLTools;
import com.smartgwt.client.data.WSDLLoadCallback;
import com.smartgwt.client.data.WebService;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.XmlNamespaces;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class WsdlDataBindingSample extends ShowcasePanel {
    private static final String DESCRIPTION = "This is an example of binding to CDYNE's Weather WebService (WSDL)." +
            " SmartGWT EE handles proxying the request seemlessly allowing users to easily talk to services from different domains.";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            WsdlDataBindingSample panel = new WsdlDataBindingSample();
            id = panel.getID();
            return panel;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
        }
    }

    public Canvas getViewPanel() {
        final Canvas canvas = new Canvas();
        canvas.setWidth100();
        canvas.setHeight100();

        final String wsdlURL = "http://ws.cdyne.com/WeatherWS/Weather.asmx?wsdl";
        final String namespaceURL = "http://ws.cdyne.com/WeatherWS/";
        final String wsOperation = "GetCityForecastByZIP";
        SC.showPrompt("Loading WSDL from: " + wsdlURL);
        XMLTools.loadWSDL(wsdlURL, new WSDLLoadCallback() {
            public void execute(WebService service) {
                if(service == null) {
                    SC.warn("WSDL not currently available from CDYNE (tried "+ wsdlURL+ ")", new BooleanCallback() {
                        public void execute(Boolean value) {
                        }
                    });
                    return;
                }

                DataSource inputDS = service.getInputDS(wsOperation);
                
                XmlNamespaces ns = new XmlNamespaces();
                ns.addNamespace("cdyne", namespaceURL);
                
                DataSource resultDS = new DataSource();
                resultDS.setServiceNamespace(namespaceURL);
                resultDS.setXmlNamespaces(ns);
                resultDS.setRecordName("Forecast");
                OperationBinding opb = new OperationBinding(DSOperationType.FETCH, wsdlURL);
                opb.setXmlNamespaces(ns);
                opb.setWsOperation(wsOperation);
                opb.setRecordName("Forecast");
                resultDS.setOperationBindings(opb);
                
                resultDS.setFields(
                    new DataSourceField("Date", FieldType.DATE) {{ 
                        setType(FieldType.DATE);
                        setAttribute("align", "left");
                    }},
                    new DataSourceField("Desciption", FieldType.TEXT),
                    new DataSourceField("MorningLow", FieldType.INTEGER, "Morning Low", 80)
                        {{ setValueXPath("cdyne:Temperatures/cdyne:MorningLow"); }},
                    new DataSourceField("DaytimeHigh", FieldType.INTEGER, "Daytime High", 80)
                        {{ setValueXPath("cdyne:Temperatures/cdyne:DaytimeHigh"); }}
                    );

                VLayout layout = new VLayout(20);
                layout.setWidth100();
                layout.setHeight100();
                layout.setLayoutMargin(40);

                final DynamicForm searchForm = new DynamicForm();
                searchForm.setNumCols(4);
                searchForm.setWidth(500);
                searchForm.setDataSource(inputDS);

                final ListGrid searchResults = new ListGrid();
                searchResults.setWidth100();
                searchResults.setDataSource(resultDS);

                IButton searchButton = new IButton("Search");
                searchButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        searchResults.fetchData(searchForm.getValuesAsCriteria());
                    }
                });

                layout.addMember(searchForm);
                layout.addMember(searchButton);
                layout.addMember(searchResults);
                canvas.addChild(layout);

                SC.clearPrompt();
            }
        });

        return canvas;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
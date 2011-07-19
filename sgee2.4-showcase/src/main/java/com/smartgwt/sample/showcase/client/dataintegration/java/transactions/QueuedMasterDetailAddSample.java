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

package com.smartgwt.sample.showcase.client.dataintegration.java.transactions;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.QueueSentCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.google.gwt.user.client.Timer;

import java.util.HashMap;
import java.util.Map;

public class QueuedMasterDetailAddSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>This example makes use of the SmartGWT EE server's support for setting DSRequest properties dynamically at runtime, based on responses to requests earlier in the same queue.</p>" +
                    "<p>Edit the order header details, then add one or more lines. When you click \"Save Order\", SmartGWT will send multiple DSRequests to the server - one to save the header " +
                    "and one each for however many lines you enter - but it will queue them so they are all sent as one HTTP request.</p>" +
                    "<p>The server will set the \"orderID\" property on each line to the unique sequence value assigned to the order header when it was written to the database. " +
                    "This happens as a result of the <value> tag in the queuedAdd_orderItem DataSource definition. You specify <value> (and the similar property <criteria>) using the " +
                    "Velocity Template Language, so the support is very flexible.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public Canvas create() {
            QueuedMasterDetailAddSample panel = new QueuedMasterDetailAddSample();
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
        DataSource queuedAddOrderDS = DataSource.get("queuedAdd_order");
        DataSource queuedAddOrderItemDS = DataSource.get("queuedAdd_orderItem");
        DataSource supplyCategoryDS = DataSource.get("supplyCategory");
        DataSource supplyItemDS = DataSource.get("supplyItem");

        final DynamicForm form = new DynamicForm();
        form.setWidth(300);
        form.setDataSource(queuedAddOrderDS);
        form.setUseAllDataSourceFields(true);

        final ListGrid listGrid = new ListGrid();
        listGrid.setHeight(224);
        listGrid.setWidth(400);
        listGrid.setDataSource(queuedAddOrderItemDS);
        listGrid.setCanEdit(true);
        listGrid.setAutoSaveEdits(false);

        ListGridField qtyField = new ListGridField("quantity", "Qty", 30);

        ListGridField categoryField = new ListGridField("categoryName", "Category");
        SelectItem categorySelectItem = new SelectItem();
        categorySelectItem.setOptionDataSource(supplyCategoryDS);
        categoryField.setEditorType(categorySelectItem);


        ListGridField itemField = new ListGridField("itemName", "Item");
        SelectItem itemSelectItem = new SelectItem();
        itemSelectItem.setOptionDataSource(supplyItemDS);
        itemField.setEditorType(itemSelectItem);

        listGrid.setFields(qtyField, categoryField, itemField);

        IButton addButton = new IButton("Add Order Line");
        addButton.setWidth(110);
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Map defaults = new HashMap();
                defaults.put("quantity", 1);
                listGrid.startEditingNew(defaults);
            }
        });

        IButton saveButton = new IButton("Save Order");
        saveButton.setWidth(100);
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RPCManager.startQueue();
                form.saveData();
                listGrid.saveAllEdits();
                RPCManager.sendQueue();
                form.editNewRecord();
                listGrid.setData((ListGridRecord[])null);
                SC.say("Order saved in single batch.");

            }
        });

        HLayout hLayout = new HLayout(10);
        hLayout.addMember(addButton);
        hLayout.addMember(saveButton);

        VLayout layout = new VLayout(20);
        layout.setAutoHeight();
        layout.addMember(form);
        layout.addMember(listGrid);
        layout.addMember(hLayout);

        final ServerCountLabel serverCountLabel = new ServerCountLabel();

        layout.addMember(serverCountLabel);

        RPCManager.setQueueSentCallback(new QueueSentCallback() {
            public void queueSent(RPCRequest[] requests) {
                serverCountLabel.incrementAndUpdate(requests);
                //flash the label
                serverCountLabel.setBackgroundColor("ffff77");
                new Timer() {
                    public void run() {
                        serverCountLabel.setBackgroundColor("ffffff");
                    }
                }.schedule(500);
            }
        });

        return layout;

    }

    class ServerCountLabel extends Label {
        private int count = 0;

        ServerCountLabel() {
            setPadding(10);
            setWidth(300);
            setHeight(40);
            setBorder("1px solid grey");
            setContents("<b>Number of server trips: 0<br>No queues sent</b>");
        }

        public void incrementAndUpdate(RPCRequest[] requests) {
            count++;
            setContents("<b>Number of server trips: " + count +
                    "<br/>Last queue contained: " + requests.length + " request(s)</b>");
        }
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
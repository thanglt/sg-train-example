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

package com.smartgwt.sample.showcase.client.dataintegration.java.sql;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class LargeValueMapSQLSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>This example shows the simple use of custom SQL clauses to provide a DataSource that joins multiple tables while retaining SmartGWT's " +
                    "automatic paging and filtering behavior. When trying this example, remember that this is <b>automatic</b> dataset-handling behavior that " +
                    "works without any coding, even though the data is being provided by a custom SQL query.</p>" +
                    "<p>The list contains order items; each order item holds an itemID, which is being used to join to the supplyItem table and obtain the " +
                    "itemName. Note that you can filter on the itemName - either select a full item name or just enter a partial value in the combo box. " +
                    "Pagination is also active - try quickly dragging the scrollbar down, and you'll see SmartGWT contacting the server for more records.</p>" +
                    "<p>Editing is also enabled in this example. Try filtering to a small sample of items, then edit one of them by double-clicking it and choose " +
                    "a different item. Note how that order item is immediately filtered out of the list: SmartGWT's intelligent cache sync also automatically " +
                    "handles custom SQL statements.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public Canvas create() {
            LargeValueMapSQLSample panel = new LargeValueMapSQLSample();
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

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        DataSource dataSource = DataSource.get("largeValueMap_orderItem");


        final ListGrid orderListGrid = new ListGrid();
        orderListGrid.setWidth(550);
        orderListGrid.setHeight(224);
        orderListGrid.setDataSource(dataSource);
        orderListGrid.setAutoFetchData(true);
        orderListGrid.setShowFilterEditor(true);
        orderListGrid.setCanEdit(true);

        ListGridField orderIdField = new ListGridField("orderID");
        ListGridField itemIdField = new ListGridField("itemID", "Item Name");
        itemIdField.setWidth("50%");
        itemIdField.setEditorType(new SelectItem());
        itemIdField.setFilterEditorType(new ComboBoxItem());
        itemIdField.setDisplayField("itemName");

        ListGridField quantityField = new ListGridField("quantity");
        ListGridField unitPriceField = new ListGridField("unitPrice");

        orderListGrid.setFields(orderIdField, itemIdField, quantityField, unitPriceField);

        return orderListGrid;
    }


    public String getIntro() {
        return DESCRIPTION;
    }


}
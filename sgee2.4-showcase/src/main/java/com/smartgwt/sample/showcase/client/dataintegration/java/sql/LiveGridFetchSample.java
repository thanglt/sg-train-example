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
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class LiveGridFetchSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>Rows are fetched <b>from the server</b> automatically as the user drags the scrollbar. <b>Double Click</b> to edit. Drag the scrollbar quickly to the bottom to " +
                    "fetch a range near the end (a prompt will appear during server fetch).</p><p>Scroll slowly back up to fill in the middle.</p>" +
                    "<p>Another key unique feature of SmartGWT is lazy rendering of <b>columns</b>. Most browsers cannot handle displaying a large number of column and have serious performance issues." +
                    "SmartGWT however does not render all columns outside the visible area by default and only renders them as you scroll horizontally. You can however disable this feature if desired.</p>" +
                    "<p>You can control how far ahead of the currently visible area rows should be rendered. This is expressed as a ratio from viewport size to rendered area size. The default is 1.3.</p>" +
                    "<p>Tweaking drawAheadRatio allows you to make tradeoffs between continuous scrolling speed vs initial render time and render time when scrolling by large amounts.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public Canvas create() {
            LiveGridFetchSample panel = new LiveGridFetchSample();
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
        DataSource dataSource = DataSource.get("supplyItem");

        ListGridField itemName = new ListGridField("itemName", 100);
        ListGridField sku = new ListGridField("SKU", 100);
        ListGridField description = new ListGridField("description", 150);
        ListGridField category = new ListGridField("category", 100);
        ListGridField units = new ListGridField("units", 100);

        ListGridField unitCost = new ListGridField("unitCost", 100);
        unitCost.setType(ListGridFieldType.FLOAT);

        ListGridField inStock = new ListGridField("inStock", 100);
        inStock.setType(ListGridFieldType.BOOLEAN);

        ListGridField nextShipment = new ListGridField("nextShipment", 100);
        nextShipment.setType(ListGridFieldType.DATE);


        final ListGrid listGrid = new ListGrid();
        listGrid.setCanEdit(true);
        listGrid.setWidth(740);
        listGrid.setHeight100();
        listGrid.setCanEdit(true);
        listGrid.setAutoFetchData(true);
        listGrid.setDataSource(dataSource);
        listGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
        listGrid.setModalEditing(true);
        listGrid.setShowRowNumbers(true);

        listGrid.setFields(itemName, sku, description, category, units, unitCost);

        return listGrid;
    }


    public String getIntro() {
        return DESCRIPTION;
    }

}
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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

import java.util.HashMap;
import java.util.Map;


public class UserSpecificDataSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows the use of some simple user-written server code in conjunction with SmartGWT databound dragging features and the SmartGWT SQL DataSource to implement a simple, but secure, shopping cart example.</p>" +
            "<p>Via DMI (Direct Method Invocation), the cartItem DataSource declares that all DataSource operations should go through a custom Java method CartDMI.enforceUserAccess() before proceeding to read or write the database.   CartDMI.enforceUserAccess() adds the current sessionId to the DSRequest, so that the user can only read and write his own shopping cart.</p>" +
            "<p>Drag items from the left-hand grid to the right-hand grid. You can edit the quantity in the right-hand grid, and you can delete records. You can verify that the example is protecting each user's data from others by running the example in two different browsers (eg one Firefox and IE) - this creates distinct sessions with separate carts.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            UserSpecificDataSample panel = new UserSpecificDataSample();
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
        DataSource supplyItemDS = DataSource.get("supplyItem");

        ListGrid supplyItemList = new ListGrid();
        supplyItemList.setWidth(300);
        supplyItemList.setHeight(224);
        supplyItemList.setAlternateRecordStyles(true);
        supplyItemList.setDataSource(supplyItemDS);
        supplyItemList.setAutoFetchData(true);
        supplyItemList.setShowFilterEditor(true);
        supplyItemList.setCanDragRecordsOut(true);
        supplyItemList.setDragDataAction(DragDataAction.COPY);

        ListGridField itemID1 = new ListGridField("itemID");
        ListGridField itemName1 = new ListGridField("itemName");
        itemName1.setWidth("75%");

        supplyItemList.setFields(itemID1, itemName1);

        DataSource carItemDS = DataSource.get("cartItem");

        ListGrid cartItemList = new ListGrid();
        cartItemList.setWidth(400);
        cartItemList.setHeight(224);
        cartItemList.setAlternateRecordStyles(true);
        cartItemList.setDataSource(carItemDS);
        cartItemList.setAutoFetchData(true);
        cartItemList.setShowFilterEditor(true);
        cartItemList.setCanAcceptDroppedRecords(true);
        cartItemList.setCanRemoveRecords(true);
        cartItemList.setCanEdit(true);

        Map dropValues = new HashMap();
        dropValues.put("quantity", 1);
        cartItemList.setDropValues(dropValues);

        ListGridField itemID2 = new ListGridField("itemID");
        itemID2.setCanEdit(false);

        ListGridField itemName2 = new ListGridField("itemName");
        itemName2.setCanEdit(false);
        itemName2.setWidth("50%");

        ListGridField quantityField = new ListGridField("quantity");
        quantityField.setDefaultValue(1f);

        cartItemList.setFields(itemID2, itemName2, quantityField);

        HLayout layout = new HLayout(15);
        layout.addMember(supplyItemList);
        layout.addMember(cartItemList);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true),
                new SourceEntity("server/CartDMI.java", JAVA, "source/ds/cart/CartDMI.java.html", true)
        };
    }
}
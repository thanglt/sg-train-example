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
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class BasicConnectorSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>The basic SQL Connector gives you the ability to immediately connect SmartGWT components to SQL databases without writing any code.</p>" +
            "<p>You can either use the SQL Wizard in Visual Builder to generate a DataSource descriptor (.ds.xml file) from an existing SQL table, or use the Admin Console to generate a SQL table from a DataSource descriptor you write. Either way, you get the immediate ability to perform all 4 basic SQL operations (select, insert, update, delete) from any of SmartGWT's data-aware components.</p>" +
            "<p>The grid below is connected to a SQL DataSource and has settings enabled to allow this grid to perform all 4 operations. Type in the input boxes above each column to do query by example. Note that data paging is automatically enabled - just scroll to load data on demand. Click on a red X to delete a record. Click on a record to edit it and click \"Add New\" to add a new record.</p>" +            
            "<p>It's easy to add business logic that takes place before and after SQL operations to enforce security or add additional data validation rules.</p>" +
            "<p>Even if your primary data storage approach is non-SQL or if you choose to use JPA or other ORM systems for most objects, the SQL connector is still valuable for initial prototypes and for lightweight storage when a full ORM approach would be overkill.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            BasicConnectorSample panel = new BasicConnectorSample();
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
        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(DataSource.get("worldDS"));
        listGrid.setAutoFetchData(true);
        listGrid.setShowFilterEditor(true);
        listGrid.setCanEdit(true);
        listGrid.setEditEvent(ListGridEditEvent.CLICK);
        listGrid.setCanRemoveRecords(true);
        
        IButton newButton = new IButton("Add New");
        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.startEditingNew();
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(listGrid);
        layout.addMember(newButton);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
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

package com.smartgwt.sample.showcase.client.doc;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.dataintegration.java.tree.TreeReparentSample;


public class GettingStartedPanel extends ShowcasePanel {
    private static final String DESCRIPTION = "Getting started with SmartGWT EE.";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            GettingStartedPanel panel = new GettingStartedPanel();
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
        HTMLPane pane = new HTMLPane();
        pane.setWidth100();
        pane.setHeight100();
        pane.setPadding(10);
        pane.setContentsURL("doc/getting-started.html");
        pane.setBorder("1px solid gray");
        return pane;
    }
}
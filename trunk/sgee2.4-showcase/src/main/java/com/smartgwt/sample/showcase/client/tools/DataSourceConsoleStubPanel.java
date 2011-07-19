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

package com.smartgwt.sample.showcase.client.tools;

import com.smartgwt.client.widgets.*;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class DataSourceConsoleStubPanel extends ShowcasePanel {

    private static final String DESCRIPTION = "<p><b>Note :</b>This Showcase doesn't include a live demo of the Database Console because it is " +
            "publicly accessible by multiple users as well as sandboxing reasons. This is enabled in the actual distibution.</p>" +
            "<p>The Database Browser connects to any configured database and allows you " +
            "to browse through both schema and data. The Database console allows you to easily configure and create DataSources.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            DataSourceConsoleStubPanel panel = new DataSourceConsoleStubPanel();
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

        VLayout layout = new VLayout(15);
        layout.setWidth100();

        Img img = new Img("dev-console.png", 666, 525);
        layout.addMember(img);
        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
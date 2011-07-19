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

package com.smartgwt.sample.showcase.client.dataintegration.java.others;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwtee.client.widgets.BatchUploader;


public class BatchUploaderSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<u>BatchUploader Example</u></h3><ul>" +
            "<li>Download the <a href=\"data/supplyItemTest.csv\" target=\"_blank\">supplyItemTest.csv</a> file to your local hard drive</li>" +
            "<li>Click the 'Browse' button and use the file picker to select the file downloaded supplyItemTest.csv file, " +
            "which is located in the <code>examples/databinding</code> folder of the SmartGWT SDK.</li>" +
            "<li>The BatchUploader will upload and validate the contents of that CSV file against the " +
            "DataSource declared on the BatchUploader, which in this case is supplyItemCustomHB.</li>" +
            "<li>Validated data will then be streamed back down to the client and displayed in an " +
            "editable ListGrid, so you can review and correct errors.</li>" +
            "<li>Click 'Commit' to save the data back to the DataSource's persistent store (in this " +
            "case, a database table accessed via Hibernate).</li>" +
            "<li>This end-to-end functionality is encapsulated by the BatchUploader, and requires " +
            "no application code.</li></ul>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            BatchUploaderSample panel = new BatchUploaderSample();
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
        DataSource dataSource = DataSource.get("supplyItemCustomHB");

        BatchUploader batchUploader = new BatchUploader();
        batchUploader.setWidth(500);
        batchUploader.setUploadDataSource(dataSource);

        TextItem stringValue = new TextItem("stringValue", "String Value");
        IntegerItem numberValue = new IntegerItem();
        numberValue.setName("numericValue");
        numberValue.setTitle("Numeric Value");

        batchUploader.setUploadFormItems(stringValue, numberValue);

        batchUploader.setDataURL(GWT.getModuleBaseURL() + "/exampleTransactionManager.do");

        ListGrid supplyItemsGrid = new ListGrid();
        supplyItemsGrid.setShowFilterEditor(true);
        supplyItemsGrid.setDataSource(dataSource);
        supplyItemsGrid.setUseAllDataSourceFields(true);
        supplyItemsGrid.setWidth100();
        supplyItemsGrid.setHeight(300);
        supplyItemsGrid.setAutoFetchData(true);

        VLayout layout = new VLayout(15);
        layout.addMember(batchUploader);

        VLayout layout2 = new VLayout();
        layout2.addMember(new Label("<b>Supply Items Table Contents</b>"));
        layout2.addMember(supplyItemsGrid);

        layout.addMember(layout2);
        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
                new SourceEntity("web.xml", XML, "source/ds/si-custom-hb/web.xml.html", true),
                new SourceEntity("showcase-servlet.xml", XML, "source/ds/si-custom-hb/showcase-servlet.xml.html", true),
                new SourceEntity("applicationContext.xml", XML, "source/ds/si-custom-hb/applicationContext.xml.html", true),
                new SourceEntity("ExampleCustomDataSource.java", JAVA, "source/ds/si-custom-hb/ExampleCustomDataSource.java.html", true)
        };
    }
}
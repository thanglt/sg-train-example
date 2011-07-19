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

package com.smartgwt.sample.showcase.client.data;

import com.smartgwt.sample.showcase.client.SmartGWTCommand;
import com.smartgwt.sample.showcase.client.cube.BasicCubeSample;
import com.smartgwt.sample.showcase.client.cube.advanced.AdvancedCubeSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.crud.*;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.*;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.*;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.*;
import com.smartgwt.sample.showcase.client.draganddrop.*;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.UploadSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.QueuedMasterDetailAddSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.SimpleQueuingSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.GridMassUpdateSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.MultiRowDragSaveSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.tree.TreeBindingSQLSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.tree.TreeReparentSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.SimpleCustomDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.ORMDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.ReusableORMDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.*;
import com.smartgwt.sample.showcase.client.doc.GettingStartedPanel;
import com.smartgwt.sample.showcase.client.doc.IntroductionPanel;
import com.smartgwt.sample.showcase.client.offline.OfflineDataSourceSupportSample;
import com.smartgwt.sample.showcase.client.tools.*;
import com.smartgwt.sample.showcase.client.webservice.RssSample;
import com.smartgwt.sample.showcase.client.webservice.WsdlDataBindingSample;
import com.smartgwt.sample.showcase.client.webservice.WsdlOperationSample;


public class ShowcaseData {

    private String idSuffix;

    public ShowcaseData(String idSuffix) {
        this.idSuffix = idSuffix;
    }

    private ExplorerTreeNode[] data;

    private ExplorerTreeNode[] getData() {
        if (data == null) {
            data = new ExplorerTreeNode[]{

                    new ExplorerTreeNode("SmartGWT EE", "doc-category", "root", "silk/book.png", null, true, idSuffix),
                    new ExplorerTreeNode("Introduction", "doc-introduction", "doc-category", "silk/book_open.png", new IntroductionPanel.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Getting Started", "doc-getting-started", "doc-category", "silk/help.png", new GettingStartedPanel.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Offline", "offline", "root", "silk/database_gear.png", null, true, idSuffix),
                    new ExplorerTreeNode("Offline DataSource support", "offline-ds", "offline", "silk/database_gear.png", new OfflineDataSourceSupportSample.Factory(), true, idSuffix),


                    new ExplorerTreeNode("Cubes", "cubes", "root", "pieces/16/cube_blue.png", null, true, idSuffix),
                    new ExplorerTreeNode("Basic CubeGrid", "cube-basic", "cubes", "pieces/16/cube_green.png", new BasicCubeSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Advanced Cube Grid (Analytics)", "cube-analytics", "cubes", "pieces/16/cube_green.png", new AdvancedCubeSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Java Data Integration", "data-integration-java", "root", "silk/database_connect.png", null, true, idSuffix),

                    new ExplorerTreeNode("Validation", "validation", "data-integration-java", "silk/server_lightning.png", null, true, idSuffix),
                    new ExplorerTreeNode("Single Source", "validation-form", "validation", "silk/table_row_delete.png", new FormValidationSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("DMI Validation", "validation-dmi", "validation", "silk/table_row_delete.png", new DMIValidationSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Velocity Expression", "validation-velocity", "validation", "silk/table_row_delete.png", new VelocityValidationSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Unique Check", "validation-unique", "validation", "silk/table_row_delete.png", new UniqueValidationSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Related Records", "validation-related", "validation", "silk/table_row_delete.png", new RelatedValidationSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Blocking Errors", "validation-blocking", "validation", "silk/table_row_delete.png", new BlockingValidationSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("SQL", "sql", "data-integration-java", "silk/server_lightning.png", null, true, idSuffix),
                    new ExplorerTreeNode("DataBase Browser", "sql-db-browser-wizard", "sql", "silk/database_gear.png", new DatabaseWizardBrowserStubPanel.Factory(), true, idSuffix),
                    new ExplorerTreeNode("DataSource Editor", "sql-db-editor-wizard", "sql", "silk/database_gear.png", new DataSourceWizardEditorStubPanel.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Basic Connector", "sql-basic-connector", "sql", "silk/database_gear.png", new BasicConnectorSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Server Advanced Filter", "filterbuilder-sql", "sql", "iconexperience/funnel.png", new ServerAdvancedFilteringSQLSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Large Value Map", "large-valuemap-sql", "sql", "silk/table_relationship.png", new LargeValueMapSQLSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("User Specific Data", "sql-user-specific-data", "sql", "silk/user_orange.png", new UserSpecificDataSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Dynamic Reporting", "sql-dynamic-reporting", "sql", "silk/table_multiple.png", new DynamicReportingSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Editable Live Grid", "livegrid-sql", "sql", "silk/application_put.png", new LiveGridFetchSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("File Upload", "upload-sql", "sql", "silk/application_put.png", new UploadSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Load Tree on Demand", "load-tree-sql", "sql", null, new TreeBindingSQLSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Tree Reparent", "tree-reparent-sql", "sql", null, new TreeReparentSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Hibernate / Beans", "hibernate", "data-integration-java", "silk/server_lightning.png", null, true, idSuffix),
                    new ExplorerTreeNode("Hibernate Wizard", "hibernate-wizard", "hibernate", "iconexperience/coffeebean.png", new HibernateWizardStubPanel.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Hibernate Connector", "hibernate-connector", "hibernate", "iconexperience/coffeebean.png", new BasicConnectorHibernateSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Server Advanced Filter", "filterbuilder-hibernate", "hibernate", "iconexperience/funnel.png", new ServerAdvancedFilteringHibernateSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Master-Detail (Batch Load &amp; Save)", "master-detail-batch", "hibernate", "silk/table_multiple.png", new MasterDetailHibernateSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Data Selection", "data-selection", "hibernate", "iconexperience/branch.png", new FlattenedDataModelSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Hibernate and Spring", "hibernate-spring", "hibernate", "silk/database_save.png", new HibernateProductionCrudSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("JavaBeans", "javabeans", "hibernate", "iconexperience/coffeebean.png", new JavaBeansGridSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Direct Method Invocation (DMI)", "dmi", "hibernate", "silk/database_edit.png", new DmiCrudSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Custom DataSources", "custom-ds", "data-integration-java", "silk/server_lightning.png", null, true, idSuffix),
                    new ExplorerTreeNode("Simple", "simple-custom-ds", "custom-ds", "silk/table_row_insert.png", new SimpleCustomDataSourceSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("ORM DataSource", "orm-ds", "custom-ds", "iconexperience/objects_exchange.png", new ORMDataSourceSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Reusable ORM DataSource", "reusable-orm-ds", "custom-ds", "iconexperience/objects_exchange.png", new ReusableORMDataSourceSample.Factory(), true, idSuffix),
                    
                    new ExplorerTreeNode("Transactions", "transactions", "data-integration-java", "silk/server_lightning.png", null, true, idSuffix),
                    new ExplorerTreeNode("Simple Queuing", "transactions-queuing", "transactions", "gears.png", new SimpleQueuingSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Queued Master / Detail Add", "transactions-queued-md", "transactions", "silk/table_row_insert.png", new QueuedMasterDetailAddSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Mass Update", "mass-update", "transactions", "silk/arrow_out.png", new GridMassUpdateSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Multi-Row Drag &amp; Save", "row-drag-save", "transactions", "silk/database_link.png", new MultiRowDragSaveSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Export", "export", "data-integration-java", "silk/server_lightning.png", null, true, idSuffix),
                    new ExplorerTreeNode("Excel Export", "excel-export", "export", "silk/page_white_excel.png", new ExcelExportSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Formatted Export", "formatted-export", "export", "silk/page_white_excel.png", new FormattedExportSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Custom Export", "custom-export", "export", "silk/page_white_excel.png", new CustomExportSample.Factory(), true, idSuffix),
                    
                    new ExplorerTreeNode("Batch File Uploader", "batch-uploader", "data-integration-java", "silk/page_white_excel.png", new BatchUploaderSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("HTTP Proxy", "http-proxy", "data-integration-java", "silk/feed.png", new RssSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Drag & Drop Data Binding", "drag-and-drop", "root", "silk/database_connect.png", null, true, idSuffix),
                    new ExplorerTreeNode("Tree Reparent", "tree-reparent", "drag-and-drop", "silk/database_refresh.png", new TreeReparent.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Recategorize (List)", "recategorize-list", "drag-and-drop", "silk/database_refresh.png", new RecategorizeList.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Recategorize (Tree)", "recategorize-tree", "drag-and-drop", "silk/database_refresh.png", new RecategorizeTree.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Recategorize (Tile)", "recategorize-tile", "drag-and-drop", "silk/database_refresh.png", new RecategorizeTile.Factory(), true, idSuffix),
                    new ExplorerTreeNode("Copy", "form-copy", "drag-and-drop", "silk/database_refresh.png", new Copy.Factory(), true, idSuffix),
                    
                    new ExplorerTreeNode("WebServices (WSDL) and RSS", "data-integration-ws-rss", "root", "silk/cog_go.png", null, true, idSuffix),
                    new ExplorerTreeNode("WSDL operation (generic)", "data-integration-server-wsdl-generic", "data-integration-ws-rss", "silk/cog_go.png", new WsdlOperationSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("WSDL databinding (Weather SOAP Search)", "data-integration-server-wsdl-weather", "data-integration-ws-rss",  "silk/cog.png", new WsdlDataBindingSample.Factory(), true, idSuffix),
                    new ExplorerTreeNode("RSS ListGrid binding", "data-integration-server-rss", "data-integration-ws-rss", "silk/feed.png", new RssSample.Factory(), true, idSuffix),

                    new ExplorerTreeNode("Tools", "tools-category", "root", "silk/wrench_orange.png", null, true, idSuffix),

                    new ExplorerTreeNode("DataSource Wizard", "tools-ds-wizard", "tools-category", "silk/database_lightning.png", new DataSourceGeneratorPanel.Factory(), true, idSuffix),
                    //new ExplorerTreeNode("DataSource Wizard", "tools-ds-wizard", "tools-category", "silk/database_lightning.png", new DataSourceGeneratorStubPanel.Factory(), true, idSuffix),

                    //new ExplorerTreeNode("DataSource Admin Console", "tools-admin-console", "tools-category", "silk/server_database.png", new DataSourceConsoleStubPanel.Factory(), true, idSuffix),
                    new CommandTreeNode("DataSource Admin Console", "tools-admin-console", "tools-category", "silk/server_database.png", new DataSourceConsoleCommand(), true, idSuffix) {
                        {
                            setDescription("SmartGWT DataSource Administrator console.");
                        }
                    },

                    //new ExplorerTreeNode("SmartClient Visual Builder", "tools-visualbuilder", "tools-category", "silk/palette.png", new VisualBuilderStubPanel.Factory(), true, idSuffix),
                    new CommandTreeNode("SmartClient Visual Builder", "tools-visualbuilder", "tools-category", "silk/palette.png", new VisualBuilderCommand(), true, idSuffix) {
                        {
                            setDescription("SmartClient's powerful WYSWIG tool with ability to connect DataSource's to DataBound components.");
                        }
                    },

                    new CommandTreeNode("Developer Console", "tools-developer-console", "tools-category", "silk/bug.png", new DebugConsoleCommand(), true, idSuffix) {
                        {
                            setDescription("SmartGWT Developer console for troubleshooting, viewing client & server logs, and more.. ");
                        }
                    },

                    new CommandTreeNode("SmartGWT Showcase", "smartgwt-category", "root", "silk/house.png", new SmartGWTCommand(), true, idSuffix),
            };
        }
        return data;
    }

    public static ExplorerTreeNode[] getData(String idSuffix) {
        return new ShowcaseData(idSuffix).getData();
    }
}

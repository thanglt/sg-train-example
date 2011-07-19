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
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.form.fields.*;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

import java.util.Date;

public class DynamicReportingSample extends ShowcasePanel {

    private static final String DESCRIPTION ="<p>This example shows the use of custom SQL clauses to build a fairly complex query, including both standard " +
            "and bespoke WHERE conditions and the use of aggregate functions and a GROUP BY. It is important to note that we can do " +
            "this whilst still keeping the normal benefits of SmartGWT DataSources, such as automatic dataset paging and arbitrary filtering and sorting. " +
            "Also note that this example, though it makes heavy use of custom SQL clauses, is also portable across different database products.</p>" +
            "<p>The list contains a summary of orders in a given date range, summarized by item - each item appears just once in the list, alongside the total " +
            "quantity of that item ordered in the given date range. Change the date range to be more restrictive (all the rows in the sample database have dates in February 2009) " +
            "and click \"Filter\", and you will see the quantities change, and items disappear from the list. You can also use the filter editor at the top of the grid " +
            "to arbitrarily filter the records, or click the column headings to sort.</p>" +
            "<p>Scroll the grid quickly to the bottom, and you will see a brief notification as SmartGWT contacts the server - pagination is still working, despite the unusual " +
            "and complex query.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public Canvas create() {
            DynamicReportingSample panel = new DynamicReportingSample();
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

        DataSource dataSource = DataSource.get("dynamicReporting_orderItem");
        
        final ListGrid orderItemSupplyList = new ListGrid();
        orderItemSupplyList.setWidth(600);
        orderItemSupplyList.setHeight(300);
        orderItemSupplyList.setAlternateRecordStyles(true);
        orderItemSupplyList.setDataSource(dataSource);
        orderItemSupplyList.setShowFilterEditor(true);

        //see the "summary" operation declared in the dynamicReporting_orderItem DataSource defintion
        orderItemSupplyList.setFetchOperation("summary");

        //deliberately small, to show server-side paging and filtering
        orderItemSupplyList.setDataPageSize(15);

        //disable this performance feature, again to force server visits
        orderItemSupplyList.setDrawAllMaxCells(0);

        ListGridField itemNameField = new ListGridField("itemID", "Item Name");
        itemNameField.setDisplayField("itemName");
        itemNameField.setWidth("50%");

        TextItem textItemEditor = new TextItem();
        textItemEditor.setFetchMissingValues(false);
        itemNameField.setFilterEditorType(textItemEditor);

        ListGridField skuField = new ListGridField("SKU");
        ListGridField unitCostField = new ListGridField("unitCost");
        ListGridField quantityField = new ListGridField("quantity");

        ListGridField totalSalesField = new ListGridField("totalSales");
        totalSalesField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(value == null) return null;
                return String.valueOf(Math.round(((Number)value).floatValue() *100) / 100);
            }
        });

        orderItemSupplyList.setFields(skuField, unitCostField, quantityField, totalSalesField);

        //create form
        final DynamicForm orderItemCriteriaForm = new DynamicForm();
        orderItemCriteriaForm.setNumCols(2);
        orderItemCriteriaForm.setWidth(400);

        DateItem startDate = new DateItem();
        startDate.setName("startDate");
        startDate.setTitle("Start&nbsp;Date");
        startDate.setDefaultValue(new Date(109, 1, 1));

        DateItem endDate = new DateItem();
        endDate.setName("endDate");
        endDate.setTitle("End&nbsp;Date");
        endDate.setDefaultValue(new Date(109, 1, 28));

        ButtonItem filterItem = new ButtonItem("filterButton", "Filter");
        filterItem.setEndRow(false);
        filterItem.setStartRow(false);
        filterItem.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Criteria criteria = orderItemSupplyList.getCriteria();
                if(criteria == null) criteria = new Criteria();
                
                Criteria formCriteria = orderItemCriteriaForm.getValuesAsCriteria();
                criteria.addCriteria(formCriteria);

                orderItemSupplyList.invalidateCache();
                orderItemSupplyList.filterData(criteria);
            }
        });

        orderItemCriteriaForm.setFields(startDate, endDate, filterItem);

        VLayout layout = new VLayout(20);
        layout.addMember(orderItemCriteriaForm);
        layout.addMember(orderItemSupplyList);

        IButton exportButton = new IButton("Export Data");
        exportButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                DSRequest dsRequestProperties = new DSRequest();
                dsRequestProperties.setOperationId("summary");
                orderItemSupplyList.exportData(dsRequestProperties);
            }
        });

        layout.addMember(exportButton);

        orderItemSupplyList.fetchData(orderItemCriteriaForm.getValuesAsCriteria());

        return layout;
    }


    public String getIntro() {
        return DESCRIPTION;
    }
}
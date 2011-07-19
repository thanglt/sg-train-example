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
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.google.gwt.i18n.client.NumberFormat;


public class ServerAdvancedFilteringSQLSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Use the FilterBuilder to construct queries of arbitrary complexity. " +
            "The FilterBuilder, and the underlying AdvancedCriteria system, support building queries with subclauses nested to any depth. " +
            "Add clauses to your query with the \"+\" icon; add nested subclauses with the \"+()\" button. " +
            "Click \"Filter\" to see the result in the ListGrid.</p>" +
            "<p>Note that this example is backed by a \"sql\" dataSource; the SmartGWT Server is automatically generating the SQL queries " +
            "required to implement the filters that the FilterBuilder can assemble. This works adaptively and seamlessly with client-side " +
            "Advanced Filtering: the generated SQL query will yield exactly the same resultset as the client-side filtering. " +
            "This means SmartGWT is able to switch to client-side filtering when its cache is full, giving a more responsive, more scalable " +
            "application.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            ServerAdvancedFilteringSQLSample panel = new ServerAdvancedFilteringSQLSample();
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

        DataSource dataSource = DataSource.get("worldDS");

        final FilterBuilder advancedFilter = new FilterBuilder();
        advancedFilter.setDataSource(dataSource);

        final ListGrid countryGrid = new ListGrid();
        countryGrid.setWidth(550);
        countryGrid.setHeight(300);
        countryGrid.setAlternateRecordStyles(true);
        countryGrid.setDataSource(dataSource);
        countryGrid.setAutoFetchData(true);

        ListGridField countryField = new ListGridField("countryName");
        ListGridField continentField = new ListGridField("continent");

        final NumberFormat nf = NumberFormat.getDecimalFormat();

        ListGridField populationField = new ListGridField("population");
        populationField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(record == null) return null;
                return nf.format(((Number)value).doubleValue());
            }
        });

        ListGridField areaField = new ListGridField("area");
        areaField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(record == null) return null;
                return nf.format(((Number)value).doubleValue());
            }
        });

        ListGridField gdpField = new ListGridField("gdp");
        gdpField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(record == null) return null;
                return nf.format(((Number)value).doubleValue());
            }
        });

        ListGridField independenceField = new ListGridField("independence");

        countryGrid.setFields(countryField, continentField, independenceField, populationField, areaField, gdpField, independenceField);

        IButton filterButton = new IButton("Filter");
        filterButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryGrid.filterData(advancedFilter.getCriteria());
            }
        });

        VStack layout = new VStack(10);
        layout.addMember(advancedFilter);
        layout.addMember(filterButton);
        layout.addMember(countryGrid);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.widgets.grid.ListGrid;

public class JsonSimpleSample implements EntryPoint {
    public void onModuleLoad() {
        DataSource dataSource = new DataSource();
        dataSource.setDataFormat(DSDataFormat.JSON);
        dataSource.setDataURL("data/dataIntegration/json/countries_small.js");

        DataSourceTextField nameField = new DataSourceTextField("name", "Name");
        DataSourceTextField populationfield = new DataSourceTextField("population", "Population");
        DataSourceTextField areaField = new DataSourceTextField("total_area", "Total Area");
        DataSourceTextField governmentField = new DataSourceTextField("government", "Government");

        dataSource.setFields(nameField, populationfield, areaField, governmentField);

        ListGrid grid = new ListGrid();
        grid.setDataSource(dataSource);
        grid.setWidth100();
        grid.setHeight(150);
        grid.setAutoFetchData(true);
        grid.draw();
    }
}

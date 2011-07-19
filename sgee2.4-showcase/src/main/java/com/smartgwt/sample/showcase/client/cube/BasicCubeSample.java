package com.smartgwt.sample.showcase.client.cube;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class BasicCubeSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This is an example of a basic CubeGrid. In this multi-dimensional dataset, each cell value has a series of " +
            "attributes, called \"facets\", that appear as stacked headers labelling the cell value. Right click on any of the facet values and select a value from the menu " +
            "to filter based on that facet value.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            BasicCubeSample panel = new BasicCubeSample();
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
        if(SC.hasAnalytics()) {
            CubeGrid cubeGrid = new CubeGrid();
            //in order to enable charting, the Drawing module must be present
            if(SC.hasDrawing()) {
                cubeGrid.setEnableCharting(true);
            }
            cubeGrid.setData(ProductRevenueData.getData());

            cubeGrid.setWidth100();
            cubeGrid.setHeight100();
            cubeGrid.setHideEmptyFacetValues(true);
//            cubeGrid.setShowCellContextMenus(true);

            final NumberFormat numberFormat = NumberFormat.getFormat("0,000");

            cubeGrid.setCellFormatter(new CellFormatter() {
                public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                    if (value == null) return null;
                    try {
                        return numberFormat.format(((Number) value).longValue());
                    } catch (Exception e) {
                        return value.toString();
                    }
                }
            });

            cubeGrid.setColumnFacets("quarter", "month", "metric");
            cubeGrid.setRowFacets("region", "product");

            return cubeGrid;
        } else {
            HTMLFlow htmlFlow = new HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled in this SDK because it requires the optional " +
            "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Analytics module</a>.</p>" +
            "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#cube-basic\" target=\"\">here</a> to see this example on smartclient.com</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("ProductRevenue.java", JAVA, "source/cube/ProductRevenue.java.html", false),
                new SourceEntity("ProductRevenueData.java", JAVA, "source/cube/ProductRevenueData.java.html", false)
        };
    }
}

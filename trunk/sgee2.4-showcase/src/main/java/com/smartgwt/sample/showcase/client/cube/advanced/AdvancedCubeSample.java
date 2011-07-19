package com.smartgwt.sample.showcase.client.cube.advanced;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoSelectionModel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.cube.FacetValueMap;
import com.smartgwt.client.widgets.cube.events.*;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class AdvancedCubeSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows binding to a multi-dimensional dataset, where each cell " +
            "value has a series of attributes, called \"facets\", that appear as headers labelling the cell value. Facets can be " +
            "added to the view, exposing more detail, by dragging the menu buttons onto the grid, or into the \"Row Facets\" and " +
            "\"Column Facets\" listings. Click grid turndown controls to expand tree facets. Note that data loads as it is revealed.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public Canvas create() {
            AdvancedCubeSample panel = new AdvancedCubeSample();
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

    private CubeGrid advancedCube;
    private Label reportLabel;
    private FacetList rowFacetList;
    private FacetList colFacetList;

    public Canvas getViewPanel() {
        if(SC.hasAnalytics()) {
            createAdvancedCube();

            Canvas controlCanvas = getFacetControls();

            // Label to show current state of CubeGrid facets
            reportLabel = new Label();
            reportLabel.setWidth(500);
            reportLabel.setHeight(30);
            reportLabel.setWrap(false);
            reportLabel.setOverflow(Overflow.VISIBLE);
            reportLabel.setPadding(5);
            reportLabel.setBorder("1px solid black");
            reportLabel.setStyleName("blackOnWhite");
            reportLabel.setAlign(Alignment.CENTER);
            reportLabel.setValign(VerticalAlignment.CENTER);
            reportLabel.setLayoutAlign(Alignment.CENTER);

            VLayout mainLayout = new VLayout();
            mainLayout.setWidth100();
            mainLayout.setHeight100();
            mainLayout.setMembersMargin(8);
            mainLayout.setLayoutMargin(10);
            mainLayout.setMembers(controlCanvas, reportLabel, advancedCube);

            updateLists();
            updateLabels();

            return mainLayout;
        } else {
            HTMLFlow htmlFlow = new HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled in this SDK because it requires the optional " +
            "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Analytics module</a>.</p>" +
            "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#cube-analytics\" target=\"\">here</a> to see this example on smartclient.com</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }
    }

    private void createAdvancedCube() {

        advancedCube = new CubeGrid();
        advancedCube.setAutoFetchData(true);

        //in order to enable charting, the Drawing module must be present
        if(SC.hasDrawing()) {
            advancedCube.setEnableCharting(true);
        }

        // Facet definitions defined in ProductRevenue
        advancedCube.setFacets(ProductRevenueFacets.getProductRevenueFacets());
        advancedCube.setDataSource(DataSource.get("productRevenue"));

        advancedCube.setValueProperty("value");
        advancedCube.setCellIdProperty("cellID");
        advancedCube.setHiliteProperty("_hilite");

        advancedCube.setRowFacets("Regions", "Products");
        advancedCube.setColumnFacets("Time");
        FacetValueMap fixedFacetValues = new FacetValueMap();

        fixedFacetValues.addMapping("Scenarios", "Budget");
        advancedCube.setFixedFacetValues(fixedFacetValues);

        advancedCube.setCanHover(true);
//        advancedCube.setShowCellContextMenus(true);

        advancedCube.setHoverCustomizer(new HoverCustomizer() {
            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum,
                                    int colNum) {
                if (record != null) {
                    return "cell value: " + record.getAttribute("value")
                            + "<br>cell ID: " + record.getAttribute("cellID");
                }
                return null;
            }
        });

        advancedCube.setHoverHeight(20);
        advancedCube.setHoverWidth(150);
        advancedCube.setValueTitle("Sales");
        advancedCube.setCanCollapseFacets(true);
        advancedCube.setCanMinimizeFacets(true);
        advancedCube.setAutoSelectValues(AutoSelectionModel.BOTH);

        advancedCube.setRowHeaderGridMode(true);
        advancedCube.setCanMoveFacets(true);

        advancedCube.addFacetAddedHandler(new FacetAddedHandler() {
            @Override
            public void onFacetAdded(FacetAddedEvent event) {
                updateLists();
                updateLabels();
            }
        });

        advancedCube.addFacetMovedHandler(new FacetMovedHandler() {
            @Override
            public void onFacetMoved(FacetMovedEvent event) {
                updateLabels();
                updateLists();
            }
        });

        advancedCube.addFixedFacetValueChangedHandler(new FixedFacetValueChangedHandler() {
            @Override
            public void onFixedFacetValueChanged(FixedFacetValueChangedEvent event) {
                updateLabels();
                updateLists();
            }
        });
    }

    private Canvas getFacetControls() {

        String[] facetIds = new String[]{"Regions", "Scenarios", "Time", "Products"};

        VLayout menuButtonStack = new VLayout();
        menuButtonStack.setWidth(175);
        menuButtonStack.setMembersMargin(20);

        // Create the FacetMenuButtons, and FacetMenus for each facet
        for (int i = 0; i < facetIds.length; i++) {
            String facetId = facetIds[i];
            FacetMenuButton button = new FacetMenuButton(facetId);
            FacetMenu menu = new FacetMenu(advancedCube, facetId);

            button.setMenu(menu);
            menuButtonStack.addMember(button);
        }

        // Create 2 ListGrids as an alternative UI for drag-rearranging facets
        rowFacetList = new FacetList(advancedCube, true);
        rowFacetList.setFields(new ListGridField("title", "Row Facets"));
        rowFacetList.setWidth(150);
        rowFacetList.setHeight(150);
        rowFacetList.setLeft(250);

        colFacetList = new FacetList(advancedCube, false);
        colFacetList.setFields(new ListGridField("title", "Column Facets"));
        colFacetList.setLeft(450);
        colFacetList.setHeight(150);
        colFacetList.setWidth(150);

        // Combine the controls into a single canvas for easy UI management

        Canvas controlCanvas = new Canvas();
        controlCanvas.setWidth(600);
        controlCanvas.setHeight(150);
        controlCanvas.setOverflow(Overflow.HIDDEN);
        controlCanvas.setLayoutAlign(Alignment.CENTER);
        controlCanvas.addChild(menuButtonStack);
        controlCanvas.addChild(rowFacetList);
        controlCanvas.addChild(colFacetList);

        return controlCanvas;
    }

    public void updateLists() {
        // Update the data for each ListGrid based on the rowFacets / columnFacets arrays
        // for the CubeGrid
        RecordList rowFacetData = new RecordList();
        String[] rowFacets = advancedCube.getRowFacets();
        for (int i = 0; i < rowFacets.length; i++) {
            Facet facet = advancedCube.getFacet(rowFacets[i]);
            ListGridRecord facetRecord = new ListGridRecord();
            facetRecord.setAttribute("id", facet.getId());
            facetRecord.setAttribute("title", facet.getTitle());

            rowFacetData.add(facetRecord);
        }
        rowFacetList.setData(rowFacetData);

        RecordList colFacetData = new RecordList();
        String[] colFacets = advancedCube.getColumnFacets();
        for (int i = 0; i < colFacets.length; i++) {
            Facet facet = advancedCube.getFacet(colFacets[i]);
            ListGridRecord facetRecord = new ListGridRecord();
            facetRecord.setAttribute("id", facet.getId());
            facetRecord.setAttribute("title", facet.getTitle());

            colFacetData.add(facetRecord);
        }
        colFacetList.setData(colFacetData);

    }

    public void updateLabels() {
        // Fixed facet values
        // Update menus and assemble descriptive string
        FacetValueMap fixedValues = advancedCube.getFixedFacetValues();
        String[] fixedFacets = fixedValues.getAttributes();
        String fixedValuesString = "<B>Report - fixed values: ";
        if (fixedFacets.length == 0) {
            fixedValuesString += "none.</B>";
        } else {
            for (int i = 0; i < fixedFacets.length; i++) {
                String facetId = fixedFacets[i];
                FacetValue fv = advancedCube.getFacetValue(facetId, fixedValues.getAttribute(facetId));

                MenuButton button = (MenuButton) Canvas.getById(facetId + "Button");
                button.setTitle("<b>" + facetId + "</b>: [" + fv.getTitle() + "]");
                fixedValuesString += (i == 0 ? " " : ", ") + facetId + ": <i>'" + fv.getTitle() + "'</i>";
            }
            fixedValuesString += "</B>";
        }
        reportLabel.setContents(fixedValuesString);

        String[] rowFacets = advancedCube.getRowFacets();
        for (int i = 0; i < rowFacets.length; i++) {
            String facetId = rowFacets[i];
            MenuButton button = (MenuButton) Canvas.getById(facetId + "Button");
            if (button != null) button.setTitle("<b>" + facetId + "</b>: [ in Rows ]");
        }

        String[] colFacets = advancedCube.getColumnFacets();
        for (int i = 0; i < colFacets.length; i++) {
            String facetId = colFacets[i];
            MenuButton button = (MenuButton) Canvas.getById(facetId + "Button");
            if (button != null) button.setTitle("<b>" + facetId + "</b>: [ in Columns ]");
        }
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("FacetList.java", JAVA, "source/cube/advanced/FacetList.java.html", false),
                new SourceEntity("FacetMenu.java", JAVA, "source/cube/advanced/FacetMenu.java.html", false),
                new SourceEntity("FacetMenuButton.java", JAVA, "source/cube/advanced/FacetMenuButton.java.html", false),
                new SourceEntity("ProductRevenueFacets.java", JAVA, "source/cube/advanced/ProductRevenueFacets.java.html", false),
        };
    }
}
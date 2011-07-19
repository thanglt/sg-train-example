package com.smartgwt.sample.showcase.client.draganddrop;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class TreeReparent extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Dragging employees between managers in this tree automatically saves the new "+
        "relationship to a DataSource, without writing any code. "+
        "Make changes, then reload the page: your changes persist.";

    public static class Factory implements PanelFactory {
        private String id;
        public Canvas create() {
            TreeReparent panel = new TreeReparent();
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
        TreeGrid tree = new TreeGrid();
        tree.setDataSource(DataSource.get("employees"));
        tree.setAutoFetchData(true);
        tree.setCanDragRecordsOut(true);
        tree.setCanAcceptDroppedRecords(true);
        return tree;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}


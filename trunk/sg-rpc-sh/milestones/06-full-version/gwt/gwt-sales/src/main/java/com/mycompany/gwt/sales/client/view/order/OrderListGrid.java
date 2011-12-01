package com.mycompany.gwt.sales.client.view.order;

import com.google.inject.Singleton;
import com.smartgwt.client.widgets.grid.ListGrid;

@Singleton
public class OrderListGrid extends ListGrid {

    public OrderListGrid() {
        setShowFilterEditor(true);

    }
}

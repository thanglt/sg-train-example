package com.skynet.spms.client.gui.supplierSupport.procurementOrder.procurementPlan.modity;

import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ProItemListGrid extends FilterListGrid {

	public ProItemListGrid() {
		this.setCanRemoveRecords(true);
		buildListGrid();
	}

	public void buildListGrid() {
		ListGridField f22 = new ListGridField("partNumber", "件号");
		ListGridField f44 = new ListGridField("plannedQuantity", "计划采购数量");
		this.setFields(f22, f44);
	}
}

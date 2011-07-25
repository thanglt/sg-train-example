package com.skynet.spms.client.gui.commonOrder.delivery.ui;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 指令明细列表
 * @author Tony FANG
 *
 */
public class DeliveryOrderItemListGrid extends ListGrid {

	public DeliveryOrderItemListGrid() {
		this.setAutoFetchData(true);
		this.setWidth100();
		this.setMargin(5);
		this.setShowFilterEditor(true);
		this.setShowRowNumbers(true);
		this.setCellHeight(22);
	}

	public void drawGrid() {
		ListGridField field2 = new ListGridField("partNumber", "件号");
		ListGridField field3 = new ListGridField("quantity", "数量");
		ListGridField field4 = new ListGridField("unitPriceAmount", "单价");
		ListGridField field5 = new ListGridField("totalAmount", "总价");
		this.setFields(field2, field3, field4, field5);
	}

}

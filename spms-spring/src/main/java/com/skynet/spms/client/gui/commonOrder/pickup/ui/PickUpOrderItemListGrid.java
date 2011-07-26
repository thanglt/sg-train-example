package com.skynet.spms.client.gui.commonOrder.pickup.ui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 订单明细列表
 * 
 * @author Tony FANG
 * 
 */
public class PickUpOrderItemListGrid extends ListGrid {

	public PickUpOrderItemListGrid() {
		this.setAutoFetchData(true);
		this.setWidth100();
		this.setMargin(5);
		this.setShowFilterEditor(true);
		this.setCellHeight(22);
	}

	public void drawGrid() {
		ListGridField field2 = new ListGridField("partNumber", "件号");
		ListGridField field3 = new ListGridField("quantity", "数量");
		ListGridField field4 = new ListGridField("unitPriceAmount", "单价");
		field4.setAlign(Alignment.RIGHT);
		ListGridField field5 = new ListGridField("totalAmount", "总价");
		field5.setAlign(Alignment.RIGHT);
		this.setFields(field2, field3, field4, field5);
	}

}

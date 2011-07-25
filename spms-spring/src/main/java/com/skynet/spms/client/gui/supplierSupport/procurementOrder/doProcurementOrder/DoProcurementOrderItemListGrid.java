package com.skynet.spms.client.gui.supplierSupport.procurementOrder.doProcurementOrder;

import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGridField;

public class DoProcurementOrderItemListGrid extends FilterListGrid {

	public DoProcurementOrderItemListGrid() {
		this.setShowRowNumbers(false);
		drawGrid();
	}

	public void drawGrid() {
		this.setCellHeight(22);

		ListGridField field2 = new ListGridField("partNumber", "件号");
		field2.setCanFilter(true);

		ListGridField field4 = new ListGridField("deliveryDate", "需求日期");
		field4.setCanFilter(true);
		field4.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field5 = new ListGridField("plannedQuantity", "采购数量");
		field5.setCanFilter(true);
		Utils.formatQuantityField(field5);

		ListGridField field7 = new ListGridField("plannedUnitPrice", "计划单价");
		field7.setCanFilter(true);
		Utils.formatPriceField(field7);

		ListGridField field8 = new ListGridField("unitPriceAmount", "金额");
		field8.setCanFilter(true);
		field8.setAlign(Alignment.RIGHT);

		ListGridField field10 = new ListGridField("remarkText", "备注");
		field10.setCanFilter(true);

		this.setFields(field2, field4, field5, field7, field8, field10);
	}

}

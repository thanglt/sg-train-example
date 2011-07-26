package com.skynet.spms.client.gui.supplierSupport.procurementOrder.procurementOrder;

import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 采购指令明细项
 * 
 * @author Tony FANG
 * 
 */
public class ProcurementOrderItemListGrid extends FilterListGrid {

	public ProcurementOrderItemListGrid() {
		this.setShowRowNumbers(false);
		drawGrid();
	}

	public void drawGrid() {

		this.setCellHeight(22);

		// ListGridField field1 = new ListGridField("itemNumber", "项号");
		// field1.setCanFilter(true);
		ListGridField field2 = new ListGridField("partNumber", "件号");
		field2.setCanFilter(true);
		// ListGridField field3 = new ListGridField("itemKeyword", "关键字");
		// field3.setCanFilter(true);
		// field3.setHidden(true);
		ListGridField field4 = new ListGridField("deliveryDate", "需求日期");
		field4.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field4.setCanFilter(true);
		ListGridField field5 = new ListGridField("plannedQuantity", "采购数量");
		Utils.formatQuantityField(field5);
		field5.setCanFilter(true);
		ListGridField field7 = new ListGridField("plannedUnitPrice", "计划单价");
		Utils.formatPriceField(field7);
		field7.setCanFilter(true);
		ListGridField field8 = new ListGridField("unitPriceAmount", "金额");
		Utils.formatPriceField(field8);
		field8.setAlign(Alignment.RIGHT);
		field8.setCanFilter(true);

		ListGridField field10 = new ListGridField("remarkText", "备注");
		field10.setCanFilter(true);
		this.setFields(/* field1, */field2, /* field3, */field4, field5,
				field7, field8, field10);
	}

}

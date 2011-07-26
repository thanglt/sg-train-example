package com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract;

import com.skynet.spms.client.gui.base.Utils;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SSLeaseContractItemListGrid extends ListGrid {

	public SSLeaseContractItemListGrid() {
		drawGrid();
		this.setAutoFetchData(true);
		// setCanRemoveRecords(true);
		// setRemoveFieldTitle("删除");
	}

	public void drawGrid() {

		// ListGridField field1 = new ListGridField("itemNumber", "项号");
		// field1.setCanFilter(true);

		ListGridField field2 = new ListGridField("partNumber", "件号");
		field2.setCanFilter(true);

		ListGridField field4 = new ListGridField("quantity", "数量");
		field4.setCanFilter(true);
		Utils.formatQuantityField(field4);

		ListGridField field6 = new ListGridField("startDate", "起租日期");
		field6.setCanFilter(true);
		field6.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field7 = new ListGridField("endDate", "结束日期");
		field7.setCanFilter(true);
		field7.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field8 = new ListGridField("leaseDays", "租赁天数");
		field8.setCanFilter(true);

		ListGridField field9 = new ListGridField("generalRentOfUnit", "标准单位租金");
		field9.setCanFilter(true);
		Utils.formatPriceField(field9);

		ListGridField field11 = new ListGridField("price", "总租金");
		field11.setCanFilter(true);
		Utils.formatPriceField(field11);

		ListGridField field12 = new ListGridField("remarkText", "备注");

		this.setFields(field2, field4, field6, field7, field8, field9, field11,
				field12);
	}
}

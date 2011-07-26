package com.skynet.spms.client.gui.customerService.leaseService.leasecontract.ui;

import com.skynet.spms.client.gui.base.Utils;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class LeaseContractItemListGrid extends ListGrid {

	public LeaseContractItemListGrid() {
		this.setCanEdit(false);
		drawGrid();
	}

	public void drawGrid() {

		ListGridField field1 = new ListGridField("partNumber", "件号");
		field1.setCanFilter(true);

		ListGridField field2 = new ListGridField("keyword", "关键字");
		field2.setCanFilter(true);

		ListGridField field4 = new ListGridField("quantity", "数量");
		Utils.formatQuantityField(field4);
		field4.setCanFilter(true);

		ListGridField field5 = new ListGridField("startDate", "起租日期");
		field5.setCanFilter(true);
		field5.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field6 = new ListGridField("endDate", "结束日期");
		field6.setCanFilter(true);
		field6.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field7 = new ListGridField("leaseDays", "租赁天数");
		field7.setCanFilter(true);

		ListGridField field8 = new ListGridField("generalRentOfUnit", "标准单位租金");
		Utils.formatPriceField(field8);
		field8.setCanFilter(true);

		ListGridField field9 = new ListGridField("factorage", "手续费");
		Utils.formatPriceField(field9);
		field9.setCanFilter(true);

		ListGridField field10 = new ListGridField("price", "金额");
		Utils.formatPriceField(field10);
		field10.setCanFilter(true);

		setFields(field1, field2, field4, field5, field6, field7, field8,
				field9, field10);
	}
}

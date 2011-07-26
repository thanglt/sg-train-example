package com.skynet.spms.client.gui.customerService.salesService.SalesRequisitionSheet;

import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SalesRequisitionSheetItemListGrid extends FilterListGrid {

	private DataSource quotationDs;

	public DataSource getSalesInquiryDs() {
		return quotationDs;
	}

	public void setSalesInquiryDs(DataSource quotationDs) {
		this.quotationDs = quotationDs;
	}

	public SalesRequisitionSheetItemListGrid() {

	}

	public void drawGrid() {
		this.setShowRowNumbers(false);
//		
//		ListGridField field1 = new ListGridField("itemNumber"/*, "项号"*/);
//		Utils.setMessageFieldWidth(field1);
		
		ListGridField field2 = new ListGridField("partNumber"/*, "件号"*/);

		ListGridField field3 = new ListGridField("keyword"/*, "关键字"*/);
		field3.setHidden(true);

		ListGridField field4 = new ListGridField("m_ManufacturerCode"/*, "制造商"*/);
		field4.setHidden(true);

		ListGridField field5 = new ListGridField("quantity"/*, "数量"*/);
		Utils.formatQuantityField(field5);
		
		ListGridField field6 = new ListGridField("unitOfPrice"/*, "单价"*/);
		Utils.formatPriceField(field6);

		ListGridField field7 = new ListGridField("amount"/*, "金额"*/);
		Utils.formatPriceField(field7);

		ListGridField field8 = new ListGridField("partDeliveryDate"/*, "交货日期"*/);
		field8.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field9 = new ListGridField("remarkText"/*, "备注"*/);
		
//		field1.setCanFilter(true);
		field2.setCanFilter(true);
		field3.setCanFilter(true);
		field4.setCanFilter(true);
		field5.setCanFilter(true);
		field6.setCanFilter(true);
		field7.setCanFilter(true);
		field8.setCanFilter(true);
		field9.setCanFilter(true);


		this.setFields( field2, field3, field4, field5, field6, field7,
				field8, field9);

		
		
		
	}

}

package com.skynet.spms.client.gui.supplierSupport.consignment.consignProtocol;

import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.customerService.commonui.BaseListGrid;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 寄售协议表格
 * 
 * @author fl
 * 
 */
public class ConsignProtocolGrid extends BaseListGrid {
	public ConsignProtocolGrid() {
		drawGrid();
	}

	public void drawGrid() {
		ListGridField field1 = new ListGridField("message"/* , "留言" */);
		Utils.setMessageFieldWidth(field1);
		/* , "协议编号" */
		ListGridField field2 = new ListGridField("contractNumber");
		field2.setCanFilter(true);
		/* , "供应商名称" */
		ListGridField field3 = new ListGridField("supplier.code");
		field3.setCanFilter(true);

		ListGridField field4 = new ListGridField("totalCount");
		field4.setCanFilter(true);
		/* , "总金额" */
		ListGridField field5 = new ListGridField("totalAmount");
		field5.setCanFilter(true);

		/* , "起始日期" */
		ListGridField field6 = new ListGridField("startDate");
		field6.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field6.setCanFilter(true);

		ListGridField field7 = new ListGridField("endDate" /* , "结束日期" */);
		field7.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field7.setCanFilter(true);

		/* 备注 */
		ListGridField field8 = new ListGridField("remarkText");
		field8.setCanFilter(true);

		ListGridField field9 = new ListGridField(
				"m_BussinessPublishStatusEntity.m_PublishStatus" /* , "发布状态" */);
		field9.setCanFilter(true);

		ListGridField field11 = new ListGridField("createDate" /* , "创建时间" */);
		field11.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field11.setCanFilter(true);

		ListGridField field12 = new ListGridField("makeBy" /* , "制定人" */);
		field12.setCanFilter(true);

		setFields(field1, field2, field3, field4, field5, field6, field7,
				field8, field9, field11, field12);
	}
}

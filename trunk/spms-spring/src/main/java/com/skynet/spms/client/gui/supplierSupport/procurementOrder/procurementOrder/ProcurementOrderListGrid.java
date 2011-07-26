package com.skynet.spms.client.gui.supplierSupport.procurementOrder.procurementOrder;

import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 主订单列表
 * 
 * @author Tony FANG
 * 
 */
public class ProcurementOrderListGrid extends FilterListGrid {

	public ProcurementOrderListGrid() {
		this.setShowRowNumbers(false);
		drawGrid();
	}

	public void drawGrid() {

		this.setCellHeight(22);

		ListGridField field1 = new ListGridField("field1", "留言");
		field1.setWidth(35);
		field1.setCanFilter(true);
		ListGridField field2 = new ListGridField("orderNumber", "采购指令编号");
		field2.setCanFilter(true);
		ListGridField field3 = new ListGridField("m_Priority", "优先级");
		field3.setWidth(60);
		field3.setCanFilter(true);
		ListGridField field4 = new ListGridField("createDate", "指令下单时间");
		field4.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field4.setCanFilter(true);
		ListGridField field5 = new ListGridField("createBy", "计划员");
		field5.setCanFilter(true);
		// ListGridField field6 = new ListGridField(
		// "m_CustomerIdentificationCode.code", "客户名称");
		// field6.setCanFilter(true);
		// ListGridField field7 = new ListGridField("deadline", "需求日期");
		// field7.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// field7.setCanFilter(true);
		ListGridField field8 = new ListGridField("procurementPlanNumber",
				"指令来源");
		field8.setCanFilter(true);
		ListGridField field88 = new ListGridField(
				"m_BussinessPublishStatusEntity.m_PublishStatus", "发布状态");
		ListGridField field9 = new ListGridField(
				"m_BussinessStatusEntity.status", "业务状态");
		field9.setWidth(60);
		field9.setCanFilter(true);
		ListGridField field10 = new ListGridField("remarkText", "备注");
		field10.setCanFilter(true);
		setFields(field1, field2, field3, field4, field5, /* field6, field7, */
		field8, field88, field9, field10);
	}

}

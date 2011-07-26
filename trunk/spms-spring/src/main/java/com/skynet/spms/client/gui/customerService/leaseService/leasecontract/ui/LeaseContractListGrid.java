package com.skynet.spms.client.gui.customerService.leaseService.leasecontract.ui;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class LeaseContractListGrid extends ListGrid {

	public LeaseContractListGrid() {
		// setSelectionType(SelectionStyle.SIMPLE);
		// setSelectionAppearance(SelectionAppearance.CHECKBOX);
		// setCanRemoveRecords(true);
		// setRemoveFieldTitle("删除");
		// this.setShowFilterEditor(true);
		drawGrid();
	}

	public void drawGrid() {

		ListGridField userGroupsField = new ListGridField("留言");
		userGroupsField.setWidth(35);

		ListGridField field1 = new ListGridField("contractNumber", "合同编号");
		field1.setCanFilter(true);

		ListGridField field2 = new ListGridField("m_Priority", "优先级");
		field2.setCanFilter(true);

		ListGridField field3 = new ListGridField("buyer.code", "客户名称");
		field3.setCanFilter(true);

		// ListGridField field4 = new ListGridField("startDate", "合同开始日期");
		// field4.setCanFilter(true);
		// field4.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		// ListGridField field5 = new ListGridField("endDate", "合同结束日期");
		// field5.setCanFilter(true);
		// field5.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field6 = new ListGridField("extendedValueTotalAmount",
				"总金额");

		ListGridField field7 = new ListGridField("makeBy", "制定人");
		field7.setCanFilter(true);

		// ListGridField field8 = new ListGridField("field8", "供应商租赁合同");
		// field8.setCanFilter(true);

		ListGridField field9 = new ListGridField(
				"m_BussinessPublishStatusEntity.m_PublishStatus", "发布状态");
		field9.setCanFilter(true);
		field9.setWidth(60);

		ListGridField field10 = new ListGridField(
				"m_BussinessStatusEntity.status", "业务状态");
		field10.setWidth(60);
		field10.setCanFilter(true);

		ListGridField field11 = new ListGridField("auditStatus", "审核状态");
		field11.setWidth(60);
		field11.setCanFilter(true);

		setFields(userGroupsField, field1, field2, field3, /* field4, field5, */
		field6, field7, /* field8, */field9, field10, field11);
	}
}

package com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SSLeaseContractListGrid extends ListGrid {

	public SSLeaseContractListGrid() {
		drawGrid();
		// setCanRemoveRecords(true);
		// setRemoveFieldTitle("删除");
	}

	public void drawGrid() {
		ListGridField field1 = new ListGridField("留言");
		field1.setCanFilter(true);

		ListGridField field2 = new ListGridField("contractNumber", "合同编号");
		field2.setCanFilter(true);

		ListGridField field3 = new ListGridField("m_Priority", "优先级");
		field3.setCanFilter(true);

		ListGridField field4 = new ListGridField("seller.code", "供应商名称");
		field4.setCanFilter(true);

		ListGridField field5 = new ListGridField("extendedValueTotalAmount",
				"合同金额");
		field5.setCanFilter(true);

		ListGridField field6 = new ListGridField("createDate", "创建日期");
		field6.setCanFilter(true);
		field6.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		ListGridField field7 = new ListGridField("makeBy", "制定人");
		field7.setCanFilter(true);

		ListGridField field8 = new ListGridField("linkman", "联系人");
		field8.setCanFilter(true);

		ListGridField field9 = new ListGridField(
				"m_BussinessPublishStatusEntity.m_PublishStatus", "发布状态");
		field9.setCanFilter(true);

		ListGridField field10 = new ListGridField(
				"m_BussinessStatusEntity.status", "业务状态");
		field10.setCanFilter(true);

		ListGridField field11 = new ListGridField("auditStatus", "审批状态");
		field11.setCanFilter(true);

		this.setFields(field1, field2, field3, field4, field5, field6, field7,
				field8, field9, field10, field11);
	}
}

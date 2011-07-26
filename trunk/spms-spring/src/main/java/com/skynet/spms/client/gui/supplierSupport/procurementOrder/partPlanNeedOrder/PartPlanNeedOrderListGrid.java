package com.skynet.spms.client.gui.supplierSupport.procurementOrder.partPlanNeedOrder;

import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 主订单列表
 * 
 * @author Tony FANG
 * 
 */
public class PartPlanNeedOrderListGrid extends FilterListGrid {

	public PartPlanNeedOrderListGrid() {
		// 自动抓取数据的关键
		this.setShowRowNumbers(false);
		drawGrid();
	}

	public void drawGrid() {

		ListGridField field1 = new ListGridField("field1", "留言");
		field1.setCanFilter(true);
		ListGridField field2 = new ListGridField("partRequirementNumber");
		field2.setCanFilter(true);
		ListGridField field3 = new ListGridField("m_ProcurementRequiredType");
		field3.setCanFilter(true);
		ListGridField field4 = new ListGridField("partNumber");
		field4.setCanFilter(true);
		// ListGridField field5 = new ListGridField("bussinessNumber");
		// field5.setCanFilter(true);
		ListGridField field6 = new ListGridField("m_Priority");
		field6.setCanFilter(true);

		ListGridField field7 = new ListGridField("expectingDeliveryDate");
		// field7.setTitle("需求日期");
		field7.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field7.setCanFilter(true);

		ListGridField field8 = new ListGridField("createBy");
		field8.setCanFilter(true);
		ListGridField field9 = new ListGridField(
				"m_BussinessStatusEntity.status");
		field9.setCanFilter(true);
		// ListGridField field10 = new ListGridField("field10", "处理方式");
		// field10.setCanFilter(true);
		setFields(field1, field2, field3, field4, /* field5, */field6, field7,
				field8, field9);

	}

}

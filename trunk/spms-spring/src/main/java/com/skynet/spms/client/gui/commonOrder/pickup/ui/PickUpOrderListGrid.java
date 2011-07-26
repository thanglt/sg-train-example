package com.skynet.spms.client.gui.commonOrder.pickup.ui;

import com.skynet.spms.client.gui.base.EnumTool;
import com.skynet.spms.client.gui.base.ItemRender;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 主订单列表
 * 
 * @author Tony FANG
 * 
 */
public class PickUpOrderListGrid extends FilterListGrid {

	public PickUpOrderListGrid() {
		this.setAutoFetchData(true);
		this.setWidth100();
		this.setMargin(5);
		this.setShowFilterEditor(true);
		this.setCellHeight(22);
	}

	public void drawGrid() {
		ListGridField field2 = new ListGridField("orderNumber", "提货指令编号");

		ListGridField field3 = new ListGridField("priority", "优先级");
		field3.setCellFormatter(new ItemRender(EnumTool.PRIORITY));

		ListGridField field4 = new ListGridField("businessType", "指令类型");
		field4.setCellFormatter(new ItemRender(EnumTool.BUSINESSTYPE));

		ListGridField field5 = new ListGridField("contractNumber", "依据合同编号");

		ListGridField field6 = new ListGridField("companyOfShipper", "发货单位");

		ListGridField field7 = new ListGridField("companyOfConsignee", "收货单位");

		ListGridField field8 = new ListGridField("shippedDate", "发货日期");
		field8.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field8.setType(ListGridFieldType.DATE);

		ListGridField field9 = new ListGridField("isPublish", "发布状态");

		field9.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if ("0".equals(value)) {
					return "未发布";
				} else {
					return "已发布";
				}
			}
		});
		ListGridField field10 = new ListGridField("createDate", "发布时间");
		field10.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		field10.setType(ListGridFieldType.DATE);

		ListGridField field11 = new ListGridField("statusName", "业务状态");
		field11.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				// (1:未处理/2:处理中/3:已处理)
				if ("1".equals(value)) {
					return "未处理";
				} else if ("2".equals(value)) {
					return "处理中";
				} else if ("3".equals(value)) {
					return "已处理";
				} else {
					return "未处理";
				}
			}
		});
		setFields(field2, field3, field4, field5, field6, field7, field8,
				field9, field10, field11);

	}

}

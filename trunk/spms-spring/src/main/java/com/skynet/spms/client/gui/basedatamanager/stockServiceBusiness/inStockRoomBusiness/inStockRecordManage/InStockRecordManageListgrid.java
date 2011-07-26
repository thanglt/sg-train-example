package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.inStockRoomBusiness.inStockRecordManage;

import java.util.logging.Logger;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class InStockRecordManageListgrid extends ListGrid {

	private Logger log = Logger.getLogger("InStockRecordManageListgrid");

	public void drawInStockRecordManageListgrid() {
		setCanRemoveRecords(true);
		setRemoveFieldTitle("删除");
		setSelectionType(SelectionStyle.SIMPLE);
		setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setCanEdit(false);
		setShowFilterEditor(true);

		// 件号
		ListGridField partNumber = new ListGridField("partNumber");
		// 序号/批号
		ListGridField partSerialNumber = new ListGridField("partSerialNumber");
		// 名称
		ListGridField keyword = new ListGridField("keyword");
		// 数量
		ListGridField quantity = new ListGridField("quantity");
		// 单位
		ListGridField unitOfMeasure = new ListGridField("unitOfMeasure");
		// 入库日期
		ListGridField inStockDate = new ListGridField("inStockDate");
		inStockDate.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// 实际货位
		ListGridField stackPositionCode = new ListGridField("stackPositionCode");
		// 收料单号
		ListGridField receivingNumber = new ListGridField("receivingSheetNumber");
		// 验收单号
		ListGridField checkAndAcceptSheetNumber = new ListGridField("checkAndAcceptSheetNumber");
		// 合同编号
		ListGridField contractNumber = new ListGridField("contratNumber");

		partNumber.setCanFilter(true);
		partSerialNumber.setCanFilter(true);
		keyword.setCanFilter(true);
		quantity.setCanFilter(true);
		unitOfMeasure.setCanFilter(true);
		inStockDate.setCanFilter(true);
		stackPositionCode.setCanFilter(true);
		receivingNumber.setCanFilter(true);
		checkAndAcceptSheetNumber.setCanFilter(true);
		contractNumber.setCanFilter(true);

		setFields( partNumber
				, partSerialNumber
				, keyword
				, quantity
				, unitOfMeasure
				, inStockDate
				, stackPositionCode
				, receivingNumber
				, checkAndAcceptSheetNumber
				, contractNumber
			    );
		setCellHeight(22);
	}
}
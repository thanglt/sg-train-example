package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.inStockRoomBusiness.inStockRecord;

import java.util.logging.Logger;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class PartsInventoryRecordListgrid extends ListGrid {

	private Logger log = Logger.getLogger("PartsInventoryRecordListgrid");

	public void drawPartsInventoryRecordListgrid()
	{
		setCanRemoveRecords(true);
		setSelectionType(SelectionStyle.SINGLE);
		setCanEdit(false);
		
		// 件号 
		ListGridField partNumberFiled = new ListGridField("partNumber");
		// 序号/批号
		ListGridField partSerialNumberFiled = new ListGridField("partSerialNumber");
		// 供货单位
		ListGridField supplyUnitFiled = new ListGridField("supplyUnit");
		// 货位编号
		ListGridField cargoSpaceNumberFiled = new ListGridField("cargoSpaceNumber");
		// 库房编号
		ListGridField stockRoomNumberFiled = new ListGridField("stockRoomNumber");
		// 区域编号
		ListGridField stockAreaNumberFiled = new ListGridField("stockAreaNumber");
		partNumberFiled.setCanFilter(true);
		partSerialNumberFiled.setCanFilter(true);
		cargoSpaceNumberFiled.setCanFilter(true);

		setFields(partNumberFiled
				,partSerialNumberFiled
				,supplyUnitFiled
				,cargoSpaceNumberFiled
				,stockRoomNumberFiled
				,stockAreaNumberFiled
				);
		setCellHeight(22);
	}
}
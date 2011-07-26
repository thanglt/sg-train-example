package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.partsInventory.timeControlPartBusiness;

import java.util.logging.Logger;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class TimeControlPartBusinessListgrid extends ListGrid {

	private Logger log = Logger.getLogger("TimeControlPartBusinessListgrid");

	public void drawTimeControlPartBusinessListgrid()
	{
		setCanRemoveRecords(true);
		setRemoveFieldTitle("删除");
		setSelectionType(SelectionStyle.SIMPLE);
		setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setCanEdit(false);
		setShowFilterEditor(true);

		// 件号
		ListGridField partNumberFiled = new ListGridField("partNumber");
		// 序号/批次
		ListGridField partSerialNumberFiled = new ListGridField("partSerialNumber");
		// 制造商
		ListGridField manufacturerCodeFiled = new ListGridField("manufacturerCode");
		// 关键字
		ListGridField keyCodeFiled = new ListGridField("keyCode");
		// 数量
		ListGridField quantityFiled = new ListGridField("quantity");
		// 单位
		ListGridField unitOfMeasureFiled = new ListGridField("unitOfMeasure");
		// 备件状况
		ListGridField partStatusCodeFiled = new ListGridField("partStatusCode");
		// 库房编号
		ListGridField stockRoomNumberFiled = new ListGridField("stockRoomNumber");
		// 货位编号
		ListGridField cargoSpaceNumberFiled = new ListGridField("cargoSpaceNumber");
		// 出厂日期
		ListGridField manufactureDateFiled = new ListGridField("manufactureDate");
		manufactureDateFiled.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// 购买日期
		ListGridField buyDateFiled = new ListGridField("buyDate");
		buyDateFiled.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// 到限日期
		ListGridField timeControlTaskEndDateFiled = new ListGridField("timeControlTaskEndDate");
		timeControlTaskEndDateFiled.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// 周期(月)
		ListGridField timeControlTaskCycleFiled = new ListGridField("timeControlTaskCycle");
		// 恢复代码 
		ListGridField renewalProcedureCodeFiled = new ListGridField("renewalProcedureCode");
		// 检测方法
		ListGridField periodicCheckCodeFiled = new ListGridField("periodicCheckCode");

		partNumberFiled.setCanFilter(true);
		partSerialNumberFiled.setCanFilter(true);
		manufacturerCodeFiled.setCanFilter(true);
		keyCodeFiled.setCanFilter(true);
		quantityFiled.setCanFilter(true);
		unitOfMeasureFiled.setCanFilter(true);
		partStatusCodeFiled.setCanFilter(true);
		stockRoomNumberFiled.setCanFilter(true);
		cargoSpaceNumberFiled.setCanFilter(true);
		manufactureDateFiled.setCanFilter(true);
		buyDateFiled.setCanFilter(true);
		timeControlTaskEndDateFiled.setCanFilter(true);
		timeControlTaskCycleFiled.setCanFilter(true);
		renewalProcedureCodeFiled.setCanFilter(true);
		periodicCheckCodeFiled.setCanFilter(true);
		
		setFields(partNumberFiled
				,partSerialNumberFiled
				,manufacturerCodeFiled
				,keyCodeFiled
				,quantityFiled
				,unitOfMeasureFiled
				,partStatusCodeFiled
				,stockRoomNumberFiled
				,cargoSpaceNumberFiled
				,manufactureDateFiled
				,buyDateFiled
				,timeControlTaskEndDateFiled
				,timeControlTaskCycleFiled
				,renewalProcedureCodeFiled
				,periodicCheckCodeFiled
				);
		setCellHeight(22);
	}
}
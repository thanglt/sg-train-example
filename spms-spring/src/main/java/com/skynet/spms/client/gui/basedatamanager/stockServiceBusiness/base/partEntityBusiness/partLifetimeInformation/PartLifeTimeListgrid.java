package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.base.partEntityBusiness.partLifetimeInformation;

import java.util.logging.Logger;

import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class PartLifeTimeListgrid extends ListGrid {

	private Logger log = Logger.getLogger("PartLifetimeInformationListgrid");

	public void drawPartLifetimeInformationListgrid()
	{
		setCanRemoveRecords(true);
		setRemoveFieldTitle("删除");
		setSelectionType(SelectionStyle.SIMPLE);
		setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setCanEdit(false);
		setShowFilterEditor(true);
		
		// 件号 
		ListGridField partNumberFiled = new ListGridField("partNumber");
		// 备件分类
		ListGridField sparePartClassCodeFiled = new ListGridField("sparePartClassCode");
		// 检测周期
		ListGridField timeControlTaskCycleFiled = new ListGridField("timeControlTaskCycle");
		// 检测代码
		ListGridField periodicCheckCodeFiled = new ListGridField("periodicCheckCode");
		// 恢复方式
		ListGridField renewalProcedureCodeFiled = new ListGridField("renewalProcedureCode");
		// 注意代码
		ListGridField payAttentionCodeFiled = new ListGridField("payAttentionCode");
		// 制造商
		ListGridField manufacturerCodeFiled = new ListGridField("manufacturerCode");
		// ATA
		ListGridField ataFiled = new ListGridField("ata");
		// 重要性
		ListGridField essentialityCodeFiled = new ListGridField("essentialityCode");

		partNumberFiled.setCanFilter(true);
		sparePartClassCodeFiled.setCanFilter(true);
		timeControlTaskCycleFiled.setCanFilter(true);
		periodicCheckCodeFiled.setCanFilter(true);
		renewalProcedureCodeFiled.setCanFilter(true);
		payAttentionCodeFiled.setCanFilter(true);
		manufacturerCodeFiled.setCanFilter(true);
		ataFiled.setCanFilter(true);
		essentialityCodeFiled.setCanFilter(true);
		
		setFields(partNumberFiled
				,sparePartClassCodeFiled
				,timeControlTaskCycleFiled
				,periodicCheckCodeFiled
				,renewalProcedureCodeFiled
				,payAttentionCodeFiled
				,manufacturerCodeFiled
				,ataFiled
				,essentialityCodeFiled
				);
		setCellHeight(22);
	}
}
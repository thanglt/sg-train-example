package com.skynet.spms.client.gui.supplierSupport.procurementOrder.partPlanNeedOrder;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 附件
 * 
 * @author tqc
 * 
 */
public class PartPlanNeedAttachmentView extends ListGrid {

	public PartPlanNeedAttachmentView() {

		setWidth100();
		setShowAllRecords(true);
		setCanEdit(false);
		setAutoFetchData(true);
		drawGrid();

	}

	public void drawGrid() {
		ListGridField itemNumberField = new ListGridField("itemId", "项号", 40);
		ListGridField titleField = new ListGridField("title", "标题");
		ListGridField descriptionField = new ListGridField("description", "描述");
		ListGridField fillNameField = new ListGridField("fileName", "文件名");
		ListGridField modifyDateField = new ListGridField("modifyDate",
				"最后修改时间");
		modifyDateField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		ListGridField operatorField = new ListGridField("operator", "修改人");

		setFields(itemNumberField, titleField, descriptionField, fillNameField,
				modifyDateField, operatorField);
	}

}

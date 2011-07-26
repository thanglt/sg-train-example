package com.skynet.spms.client.gui.customerService.leaseService.leaseRequisitionSheet.modify;

import com.skynet.spms.client.gui.base.BaseWindow;
import com.skynet.spms.client.gui.base.CodeRPCTool;
import com.skynet.spms.client.gui.base.EnumTool;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.customerService.leaseService.leaseRequisitionSheet.LeaseRequisitionSheetItemListGrid;
import com.skynet.spms.client.gui.customerService.leaseService.model.MainModelLocator;
import com.skynet.spms.client.vo.PartInfoVO;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.skynet.spms.client.widgets.form.fields.DicSelectItem;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LeaseRequisitionSheetModifyWindow extends BaseWindow {
	final LayoutDynamicForm form = new LayoutDynamicForm();

	private MainModelLocator modelLocator;
	private LeaseRequisitionSheetItemListGrid itemlist;

	private IButton saveItemBtn;

	public LeaseRequisitionSheetModifyWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
		super(windowTitle, isMax, srcRect, listGrid, iconUrl);
	}

	@Override
	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {
		modelLocator = MainModelLocator.getInstance();
		VLayout v = new VLayout();
		// v.setWidth100();
		// v.setHeight100();
		v.setMembers(buildForm(), buildListGrid(), buildLeaseItemForm());
		return v;
	}

	TextItem item5;
	TextAreaItem item6;

	// 加载租赁申请单Form
	private Canvas buildForm() {
		VLayout v = new VLayout();
		v.setHeight(150);
		HLayout h1 = new HLayout();
		final LayoutDynamicForm form = new LayoutDynamicForm();
		form.setNumCols(6);
		form.setWidth100();
		form.setDataSource(modelLocator.leaseRequisitionSheetListGrid
				.getDataSource());
		form.reset();
		form.editSelectedData(modelLocator.leaseRequisitionSheetListGrid);

		TextItem item1 = new TextItem();
		item1.setName("requisitionSheetNumber");
		item1.setTitle("申请单编号");
		item1.setAttribute("readOnly", true);

		SelectItem item2 = new SelectItem();
		item2.setTitle("优先级");
		item2.setName("m_Priority");

		DicSelectItem item3 = new DicSelectItem();
		item3.setTitle("飞机机尾号");
		item3.setName("airIdentificationNumber");

		final SelectItem item4 = new SelectItem();
		item4.setTitle("客户名称");
		item4.setName("m_CustomerIdentificationCode.id");
		CodeRPCTool.bindData(CodeRPCTool.CUSTOMERIDENTIFICATIONCODE, item4);
		item4.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				String id = item4.getValueAsString();
				CodeRPCTool.bindCustomerData(id, item5, item6);
			}
		});

		item5 = new TextItem();
		item5.setTitle("联系人");
		item5.setName("linkman");

		item6 = new TextAreaItem();
		item6.setTitle("联系方式");
		item6.setName("contactWay");
		item6.setRowSpan(2);
		item6.setHeight(80);

		TextAreaItem item7 = new TextAreaItem();
		item7.setTitle("备注");
		item7.setName("remark");
		item7.setHeight(50);
		form.setFields(item1, item2, item3, item4, item5, item6, item7);
		h1.addMember(form);

		HLayout h2 = new HLayout();
		h2.setHeight(20);

		IButton saveBtn = new IButton("保存");
		saveBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (form.validate()) {
					form.saveData(new DSCallback() {
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							SC.say("保存成功!");
						}
					});
				}
			}
		});

		h2.addMember(saveBtn);
		v.setMembers(h1, h2);
		return v;
	}

	// 加载租赁申请单明细的ListGrid
	private Canvas buildListGrid() {
		VLayout v = new VLayout();
		v.setHeight(100);
		HLayout h1 = new HLayout();
		h1.setHeight(20);
		Label lbl = new Label("租赁申请单明细");
		h1.addMember(lbl);
		HLayout h2 = new HLayout();
		itemlist = new LeaseRequisitionSheetItemListGrid();
		itemlist.drawGrid();
		itemlist
				.setDataSource(modelLocator.LeaseRequisitionSheetItemdataSource);
		Criteria criteria = new Criteria();
		criteria.addCriteria("id", modelLocator.leaseRequisitionSheetListGrid
				.getSelectedRecord().getAttribute("id"));
		criteria.addCriteria("_r", String.valueOf(Math.random()));
		itemlist.fetchData(criteria);
		itemlist.drawGrid();

		itemlist.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				form.reset();
				form.editSelectedData(itemlist);
			}
		});

		h2.addMember(itemlist);
		v.setMembers(h1, h2);
		return v;
	}

	SelectItem item2;
	TextItem item3;
	SelectItem item7;
	SelectItem item99;

	// 加载租令申请单明细项的Form
	private Canvas buildLeaseItemForm() {
		VLayout v = new VLayout();

		v.setGroupTitle("租赁申请单明细项");
		v.setIsGroup(true);

		HLayout h1 = new HLayout();

		form.setNumCols(8);
		form.setWidth100();

		// form.setDataSource(modelLocator.LeaseRequisitionSheetItemdataSource);

		final TextItem leaseid = new TextItem();
		leaseid.setVisible(false);
		// leaseid.setName("leaseRequisitionSheet.id");

		final SelectItem item1 = Utils.getPartNumberList();
		item1.setName("partNumber");
		// item1.addChangedHandler(new ChangedHandler() {
		// public void onChanged(ChangedEvent event) {
		// PartInfoVO partInfo = Utils.getPartInfoVO(item1);
		// item2.setValue(partInfo.getManufacturerCodeId());
		// item3.setValue(partInfo.getKeyword());
		// item7.setValue(partInfo.getSuitableAircraftModel());
		// item99.setValue(partInfo.getUnitOfMeasureCode());
		// }
		// });

		item2 = new SelectItem();
		// item2.setName("m_ManufacturerCode.id");
		item2.setTitle("制造商");
		// CodeRPCTool.bindData(CodeRPCTool.MANUFACTURERCODE, item2);

		item3 = new TextItem();
		// item3.setName("keyword");

		DicSelectItem item4 = new DicSelectItem();
		// item4.setName("m_CertificateType");
		// item4.setMultiple(true);
		// EnumTool.setValueMap(EnumTool.CERTIFICATETYPE, item4);

		DicSelectItem item5 = new DicSelectItem();
		// item5.setName("m_ConditionCode");

		item7 = new DicSelectItem();
		// item7.setName("m_ModelofApplicabilityCode");

		TextItem item8 = new TextItem();
		// item8.setName("quantity");

		DateItem item9 = new DateItem();
		// item9.setName("startDate");
		// item9.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// item9.setUseTextField(true);

		item99 = new DicSelectItem();
		// item99.setName("m_UnitOfMeasureCode");

		DateItem item10 = new DateItem();
		// item10.setName("endDate");
		// item10.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		// item10.setUseTextField(true);

		TextAreaItem item11 = new TextAreaItem();
		// item11.setName("remarkText");
		// item11.setRowSpan(2);
		// item11.setHeight(50);

		TextItem item12 = new TextItem();
		// item12.setName("leaseDays");

		form.setFields(leaseid, item1, item2, item3, item4, item5, item7,
				item8, item9, item99, item10, item11, item12);
		h1.addMember(form);

		HLayout h2 = new HLayout();
		h2.setHeight(20);
		saveItemBtn = new IButton("保存明细");
		saveItemBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				leaseid.setValue(modelLocator.leaseRequisitionSheetListGrid
						.getSelectedRecord().getAttribute("id"));
				if (form.validate()) {
					form.saveData(new DSCallback() {
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Criteria criteria = new Criteria();
							criteria.addCriteria("id",
									modelLocator.leaseRequisitionSheetListGrid
											.getSelectedRecord().getAttribute(
													"id"));
							criteria.addCriteria("_r", String.valueOf(Math
									.random()));
							itemlist.fetchData(criteria);
							itemlist.drawGrid();
							SC.say("保存成功!");

						}
					});
				} else {

				}
			}
		});
		IButton closeBtn = new IButton("关闭");
		closeBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		h2.setMembers(saveItemBtn, closeBtn);
		v.setMembers(h1, h2);
		return v;
	}

}

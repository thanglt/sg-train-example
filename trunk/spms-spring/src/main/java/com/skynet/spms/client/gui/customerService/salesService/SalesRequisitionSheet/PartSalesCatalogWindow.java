package com.skynet.spms.client.gui.customerService.salesService.SalesRequisitionSheet;

import com.skynet.spms.client.gui.base.BaseWindow;
import com.skynet.spms.client.gui.base.BtnsHLayout;
import com.skynet.spms.client.gui.base.CustomPanel;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 备件销售目录
 * 
 * @author Tony FANG
 * 
 */
public class PartSalesCatalogWindow extends BaseWindow {

	public PartSalesCatalogWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
		super(windowTitle, isMax, srcRect, listGrid, iconUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {
		this.setTitle("【接口】客户下订单");
		/** 主面板 **/
		VLayout vmain = new VLayout();
		vmain.setWidth100();
		vmain.setHeight100();
		vmain.setMembersMargin(4);
		vmain.setLayoutMargin(5);

		/** 面板1(显示件号) **/
		VLayout oneView = new VLayout();
		oneView.setWidth100();
		oneView.addMember(getPartNumberView());

		/** 面板2(备件库存信息) **/
		HLayout twoView = new HLayout();
		twoView.setWidth100();
		twoView.addMember(getPartInventoryView());

		/** 面板3(备件采购计划信息) **/
		VLayout threeView = new VLayout();
		threeView.setWidth100();
		threeView.addMember(getPartProcurementPlanView());

		/** 面板4(备件销售订单信息) **/
		VLayout fourView = new VLayout();
		fourView.setWidth100();
		fourView.addMember(getPartSalesPlanView());

		/** 面板5(备件采购在途信息) **/
		VLayout fiveView = new VLayout();
		fiveView.setWidth100();
		fourView.addMember(getPartProcurementTrackView());

		/** 面板6(操作按钮) **/
		VLayout sixView = new VLayout();
		sixView.setHeight(15);
		sixView.setWidth100();
		sixView.setMembersMargin(5);
		sixView.setBorder("1px solid #E8E8E8");
		sixView.setWidth100();
		sixView.addMember(btnsView());

		vmain.addMember(oneView);
		vmain.addMember(twoView);
		vmain.addMember(threeView);
		vmain.addMember(fourView);
		vmain.addMember(fiveView);
		vmain.addMember(sixView);

		return vmain;
	}

	/** 布局一(获得件号视图) **/
	private DynamicForm getPartNumberView() {
		DynamicForm ldf = new DynamicForm();
		TextItem item1 = new TextItem();
		item1.setName("item1");
		item1.setTitle("件号");
		item1.setTitleAlign(Alignment.LEFT);
		item1.setDisabled(true);
		item1.setWidth(150);
		ldf.setFields(item1);
		return ldf;
	}

	/** 布局二(备件库存信息) **/
	private Canvas getPartInventoryView() {
		CustomPanel cp = new CustomPanel();
		cp.setTitleLabelContents("备件库存信息");
		/** 列表信息 **/
		ListGrid lg = new ListGrid();
		lg.setWidth100();
		lg.setAutoFetchData(true);
		ListGridField field1 = new ListGridField("field1", "库存位置");

		ListGridField field2 = new ListGridField("field2", "库存量");

		lg.setFields(field1, field2);
		cp.getContentCanvas().addChild(lg);
		return cp;
	}

	/** 布局三(备件采购计划信息) **/
	private Canvas getPartProcurementPlanView() {
		CustomPanel cp = new CustomPanel();
		cp.setTitleLabelContents("备件采购计划信息");
		/** 列表信息 **/
		ListGrid lg = new ListGrid() {
			/**
			 * 渲染Grid列
			 */
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {

				String fieldName = this.getFieldName(colNum);
				// 采购计划数量
				if (fieldName.equals("CGJHSL")) {
					final LayoutDynamicForm form = new LayoutDynamicForm();
					form.setCellPadding(0);
					form.setAlign(Alignment.CENTER);

					LinkItem item1 = new LinkItem();
					item1.setShowTitle(false);
					item1.setLinkTitle(record.getAttribute("field1"));
					item1.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							// ProcurementPlanView win = new
							// ProcurementPlanView(
							// "采购计划详细信息", true, null, null, null);
							// ShowWindowTools.showWindow(form.getPageRect(),
							// win,
							// 200);
						}
					});

					form.setFields(item1);
					return form;
				}
				return null;
			}

		};
		lg.setWidth100();
		lg.setAutoFetchData(true);
		// 启动可渲染
		lg.setShowRecordComponents(true);
		lg.setShowRecordComponentsByCell(true);
		ListGridField field1 = new ListGridField("CGJHSL", "采购计划数量");

		ListGridField field2 = new ListGridField("field2", "采购数量");

		ListGridField field3 = new ListGridField("field3", "发布时间");

		ListGridField field4 = new ListGridField("field4", "业务状态");

		lg.setFields(field1, field2, field3, field4);

		lg.setDataSource(Utils.getXmlDataSource());

		cp.getContentCanvas().addChild(lg);
		return cp;
	}

	/** 布局四(备件销售合同信息) **/
	private Canvas getPartSalesPlanView() {
		CustomPanel cp = new CustomPanel();
		cp.setTitleLabelContents("备件销售订单信息");
		/** 列表信息 **/
		ListGrid lg = new ListGrid() {
			/**
			 * 渲染Grid列
			 */
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {

				String fieldName = this.getFieldName(colNum);
				// 销售合同号
				if (fieldName.equals("XSHTH")) {
					final LayoutDynamicForm form = new LayoutDynamicForm();
					form.setCellPadding(0);
					form.setAlign(Alignment.CENTER);

					LinkItem item1 = new LinkItem();
					item1.setShowTitle(false);
					item1.setLinkTitle(record.getAttribute("field1"));
					item1.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							// SalesRequisitionSheetViewWindow win = new
							// SalesRequisitionSheetViewWindow(
							// "查看销售合同详细信息", true, null, null, null);
							// ShowWindowTools.showWindow(form.getPageRect(),
							// win,
							// 200);
						}

					});

					form.setFields(item1);
					return form;
				}
				return null;
			}

		};
		lg.setWidth100();
		lg.setAutoFetchData(true);
		// 启动可渲染
		lg.setShowRecordComponents(true);
		lg.setShowRecordComponentsByCell(true);
		ListGridField field1 = new ListGridField("XSHTH", "销售合同号");

		ListGridField field2 = new ListGridField("field2", "销售数量");

		ListGridField field3 = new ListGridField("field3", "发布时间");

		ListGridField field4 = new ListGridField("field4", "业务状态");

		lg.setFields(field1, field2, field3, field4);

		lg.setDataSource(Utils.getXmlDataSource());

		cp.getContentCanvas().addChild(lg);
		return cp;
	}

	/** 布局五(备件采购在途信息) **/
	private Canvas getPartProcurementTrackView() {
		CustomPanel cp = new CustomPanel();
		cp.setTitleLabelContents("备件采购在途信息");
		/** 列表信息 **/
		ListGrid lg = new ListGrid() {
			/**
			 * 渲染Grid列
			 */
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {

				String fieldName = this.getFieldName(colNum);
				// 采购合同
				if (fieldName.equals("XSHTH")) {
					final LayoutDynamicForm form = new LayoutDynamicForm();
					form.setCellPadding(0);
					form.setAlign(Alignment.CENTER);

					LinkItem item1 = new LinkItem();
					item1.setShowTitle(false);
					item1.setLinkTitle(record.getAttribute("field1"));

					item1.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							// ProcurementContractWindowView win = new
							// ProcurementContractWindowView(
							// "查看采购合同详细信息", true, null, null, null);
							// ShowWindowTools.showWindow(form.getPageRect(),
							// win,
							// 200);
						}

					});

					form.setFields(item1);
					return form;
				}
				return null;
			}

		};
		lg.setWidth100();
		lg.setAutoFetchData(true);

		// 启动可渲染
		lg.setShowRecordComponents(true);
		lg.setShowRecordComponentsByCell(true);
		ListGridField field1 = new ListGridField("XSHTH", "销售合同号");

		ListGridField field2 = new ListGridField("field2", "销售数量");

		ListGridField field3 = new ListGridField("field3", "发布时间");

		ListGridField field4 = new ListGridField("field4", "业务状态");

		lg.setFields(field1, field2, field3, field4);

		lg.setDataSource(Utils.getXmlDataSource());

		cp.getContentCanvas().addChild(lg);
		return cp;
	}

	/**
	 * 按钮操作试图
	 * 
	 * @return
	 */
	private HLayout btnsView() {
		BtnsHLayout h = new BtnsHLayout();
		IButton closeBtn = new IButton("关闭");
		closeBtn
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						destroy();
					}
				});
		h.addMember(closeBtn);
		return h;
	}

}

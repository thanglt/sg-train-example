package com.skynet.spms.client.gui.supplierSupport.procurementOrder.suppliersParity;

import com.google.gwt.user.client.ui.Label;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.base.BaseWindow;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.supplierSupport.common.DSKey;
import com.skynet.spms.client.gui.supplierSupport.procurementOrder.suppliersParity.model.SuppliersParityModel;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 查看比价单
 * 
 * @author Tony FANG
 * 
 */
public class SuppliersParityShowWindow extends BaseWindow {


	private FilterListGrid oldSuppliersParityLG;
	
	private String suppliersParityItemIds;
	
	
	SuppliersParityModel model ;

	public SuppliersParityShowWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
		super(windowTitle, isMax, srcRect, listGrid, iconUrl);
		this.setTitle(windowTitle);
	}

	@Override
	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {
		model=SuppliersParityModel.getInstance();
		if("".equals(model.contractId)||model.contractId==null){
			model.contractId="-1";
		}
		this.setTitle("查看比价单");
		/** 主面板 **/
		VLayout vmain = new VLayout();
		vmain.setWidth100();
		vmain.setHeight100();
		vmain.setOverflow(Overflow.AUTO);

		/** 面板1 **/
		VLayout firstView = new VLayout();
		firstView.setLayoutTopMargin(10);
		//合同明细标题
		Label leftLb = new Label("合同项列表");
		leftLb.setHeight("20");
		firstView.addMember(leftLb);// 先放label
		//合同明细列表
		firstView.addMember(getListGrid());

		/** 面板2 **/
		VLayout twoView = new VLayout();
		//比价单标题
		Label twoLb = new Label("比价信息");
		twoLb.setHeight("20");
		twoView.addMember(twoLb);// 先放label
		//比价单列表
		twoView.addMember(getDetailView());

		vmain.addMember(firstView);
		vmain.addMember(twoView);

		return vmain;
	}

	// 合同明细
	private ListGrid getListGrid() {
		final FilterListGrid lg = new FilterListGrid();
		lg.setShowFilterEditor(false);
		final DataSourceTool dataSourceTool = new DataSourceTool();
		
		dataSourceTool.onCreateDataSource(DSKey.S_PROCUREMENTCONTRACT,
				DSKey.S_PROCUREMENTCONTRACTITEM_DS, new PostDataSourceInit() {
					@Override
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						
						lg.setDataSource(dataSource);
						Criteria criteria = new Criteria();
						criteria.setAttribute("template.id", model.contractId);
						criteria.setAttribute("_r",
								String.valueOf(Math.random()));
						lg.fetchData(criteria);
						
						ListGridField field2 = new ListGridField("partNumber", "件号");

						ListGridField field4 = new ListGridField("deliveryDate", "交货日期");
						field4.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

						ListGridField field5 = new ListGridField("quantity", "采购数量");
						field5.setAlign(Alignment.RIGHT);
						Utils.formatQuantityField(field5);

						ListGridField field6 = new ListGridField("m_UnitOfMeasureCode", "单价");
						field6.setAlign(Alignment.RIGHT);
						Utils.formatQuantityField(field6);

						ListGridField field7 = new ListGridField("amount", "金额");
						field7.setAlign(Alignment.RIGHT);
						Utils.formatQuantityField(field7);

						ListGridField field8 = new ListGridField("remarkText", "备注");

						lg.setFields(field2, field4, field5, field6, field7, field8);

						
						lg.addCellClickHandler(new CellClickHandler() {

							public void onCellClick(CellClickEvent event) {
								
								suppliersParityItemIds=lg.getSelectedRecord().getAttribute("suppliersParityItemIds");
								refreshOldSuppliersPairtyLG();
								
							}

						});
					}
				});
		

		return lg;
	}

	/**
	 * 详细信息
	 * 
	 * @return
	 */
	private HLayout getDetailView() {
		oldSuppliersParityLG= new FilterListGrid();
		HLayout h =new HLayout();
		// 初始化订单列表数据源
		final DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource(DSKey.S_PROCUREMENTQUOTATIONHEET_ITEM,
				DSKey.S_PROCUREMENTQUOTATIONHEET_ITEM_DS,
				new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						oldSuppliersParityLG.setDataSource(dataSource);

						refreshOldSuppliersPairtyLG();// 刷新数据源

						ListGridField field1 = new ListGridField(
								"procurementQuotationSheetRecord.supplierCode.code",
								"供应商代码(CAGE码)");

						ListGridField field2 = new ListGridField(
								"procurementQuotationSheetRecord.supplierCode.code",
								"供应商名称");

						ListGridField field3 = new ListGridField("m_Priority",
								"优先级");

						ListGridField field4 = new ListGridField(
								"quotationPartNumber", "报价件号");


						ListGridField field6 = new ListGridField("amount", "报价");

						ListGridField field7 = new ListGridField(
								"m_InternationalCurrencyCode", "币种");

						ListGridField field8 = new ListGridField(
								"deliveryLeadTime", "交货期(天)");

						ListGridField field12 = new ListGridField("createDate",
								"报价日期");
						field12
								.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

//
						field1.setCanFilter(true);
						field2.setCanFilter(true);
						field3.setCanFilter(true);
						field4.setCanFilter(true);
						field6.setCanFilter(true);
						field7.setCanFilter(true);
						field8.setCanFilter(true);
						field12.setCanFilter(true);
						oldSuppliersParityLG.setFields(field1, field2, field3, field4,
								field6, field7, field8, field12);
						
						
						
					
					}
				});

		h.addMember(oldSuppliersParityLG);
		return h;
	}

	/**
	 * 刷新已经绑定比价单列表
	 */
	private void refreshOldSuppliersPairtyLG() {
		if (suppliersParityItemIds == null||"".equals(suppliersParityItemIds)) {
			suppliersParityItemIds = "-1";
		}
		Criteria criteria = new Criteria();
		criteria.setAttribute("suppliersParityItemIds", suppliersParityItemIds);
		criteria.setAttribute("_r", String.valueOf(Math.random()));
		oldSuppliersParityLG.fetchData(criteria);
	}
	
}

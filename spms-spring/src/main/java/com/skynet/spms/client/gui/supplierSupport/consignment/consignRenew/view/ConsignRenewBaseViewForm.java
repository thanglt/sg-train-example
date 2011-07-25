package com.skynet.spms.client.gui.supplierSupport.consignment.consignRenew.view;

import com.google.gwt.user.client.Timer;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.base.CodeRPCTool;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.customerService.commonui.BaseListGrid;
import com.skynet.spms.client.gui.supplierSupport.common.DSKey;
import com.skynet.spms.client.gui.supplierSupport.consignment.consignRenew.model.ConsignRenewModel;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 寄售补库基础信息
 * 
 * @author fl
 * 
 */
public class ConsignRenewBaseViewForm extends VLayout {
	public LayoutDynamicForm form = new LayoutDynamicForm();
	public ConsignRenewModel model = ConsignRenewModel.getInstance();
	private BaseListGrid itemGrid;

	public ConsignRenewBaseViewForm(BaseListGrid itemGrid) {
		form.setNumCols(6);
		this.itemGrid = itemGrid;
		// 为Form绑定数据源
		DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource(DSKey.S_CONSIGNRENEW,
				DSKey.S_CONSIGNRENEW_DS, new PostDataSourceInit() {
					@Override
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						form.setDataSource(dataSource);
						Criteria criteria = new Criteria();
						StringBuilder builder = new StringBuilder();
						builder.append(model.consignRenewId);
						criteria.setAttribute("id", builder.toString());
						form.fetchData(criteria);
						buildFormFields();
						build();
						Utils.setReadOnlyForm(form);
					}
				});
	}

	/**
	 * 寄售补库协议主键
	 */
	public TextItem protocolIdItems;
	/**
	 * 寄售补库协议编号
	 */
	public TextItem protocolNumberItem;
	/**
	 * 寄售地点
	 */
	public TextItem consignAddr;
	/**
	 * 供应商
	 */
	public SelectItem provider;
	/**
	 * 库房
	 */
	public SelectItem depot;
	/**
	 * 交货日期
	 */
	public DateItem deliveryTypeDate;
	/** 编号输入框 */
	public TextItem item_no;
	/** 联系人 */
	public TextItem linkManItem;
	/** 贸易方式 */
	public SelectItem tradeMethodItem;
	/** 联系方式 */
	public TextItem linkWayItem;
	/** 是否买方指定运代复选框 */
	public CheckboxItem isBuyerFreightAgentItem;
	/** 运代商输入框 */
	public SelectItem carrierNameItem;
	/** 运代商联系方式输入框 */
	public TextItem carrierLinkWayItem;
	/** 交货方式 */
	public SelectItem deliveryTypeItem;
	/** 运代商联系人输入框 */
	public TextItem carrierLinkManItem;
	/** 运输方式下拉列表 */
	public SelectItem transportationCodeItem;
	/** 数量总计入框 **/
	public TextItem totalCount;
	/** 金额总计输入框 **/
	public TextItem totalPrice;

	/**
	 * 寄售补库原因
	 */
	private TextItem resean;
	/**
	 * 供应商补库单号
	 */
	private TextItem supplierRenewNum;

	/** 按钮组面板 **/
	public HLayout btnGroup;
	public IButton btnClose;

	/**
	 * 将表单和按钮组添加进来
	 */
	public void build() {
		this.addMember(form);
		this.addMember(btnGroup);
	}

	public void buildFormFields() {
		/**
		 * 初始化数量总计
		 */
		totalCount = new TextItem("totalCount"/* , "项数总计" */);
		totalCount.setDisabled(true);
		/**
		 * 初始化金额总计
		 */
		totalPrice = new TextItem("totalAmount"/* , "金额总计" */);
		totalPrice.setDisabled(true);
		/**
		 * 初始化贸易方式下拉列表
		 */
		tradeMethodItem = new SelectItem("m_TradeMethods"/* , "贸易方式" */);
		/**
		 * 初始化运代商联系人输入框
		 */
		carrierLinkManItem = new TextItem("appointForwarderLinkman"/* , "联系人" */);
		carrierLinkManItem.setDisabled(true);
		/**
		 * 初始化运代商联系方式输入框
		 */
		carrierLinkWayItem = new TextItem("appointForwarderContact"/* , "联系方式" */);
		carrierLinkWayItem.setColSpan(3);
		carrierLinkWayItem.setDisabled(true);
		/**
		 * 初始化运代商输入框
		 */
		carrierNameItem = new SelectItem();
		carrierNameItem.setName("carrierName.id");
		CodeRPCTool.bindData(CodeRPCTool.CARRIERNAMECODE, carrierNameItem);
		carrierNameItem.setDisabled(true);
		carrierNameItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String id = carrierNameItem.getValueAsString();
				CodeRPCTool.bindCarrierData(id, carrierLinkWayItem,
						carrierLinkManItem);
			}
		});
		/**
		 * 初始化运输方式下拉列表
		 */
		transportationCodeItem = new SelectItem();
		// transportationCodeItem.setTitle("运输方式");
		transportationCodeItem.setName("m_TransportationCode");
		/**
		 * 初始化是否买方指定运代复选框
		 */
		isBuyerFreightAgentItem = new CheckboxItem();
		isBuyerFreightAgentItem.setName("buyerFreightAgent");
		isBuyerFreightAgentItem.setColSpan(5);
		isBuyerFreightAgentItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				boolean sel = isBuyerFreightAgentItem.getValueAsBoolean();
				boolean enabled = false;
				if (sel) {
					enabled = false;
				} else {
					enabled = true;
				}
				carrierLinkManItem.setDisabled(enabled);
				carrierLinkWayItem.setDisabled(enabled);
				carrierNameItem.setDisabled(enabled);
			}
		});
		/**
		 * 初始化交货方式下拉列表
		 */
		deliveryTypeItem = new SelectItem();
		deliveryTypeItem.setName("m_DeliveryType");
		/**
		 * 初始化联系方式输入框
		 */
		linkWayItem = new TextItem("contactWay");
		linkWayItem.setColSpan(3);
		/**
		 * 初始化联系人输入框
		 */
		linkManItem = new TextItem();
		linkManItem.setName("linkman");
		linkManItem.setRequired(true);
		/**
		 * 初始化业务编号输入框
		 */
		item_no = new TextItem();
		item_no.setName("requisitionSheetNumber");
		item_no.setDisabled(true);
		item_no.setValue("业务编号系统自动生成");

		protocolIdItems = new TextItem("template.id");
		protocolIdItems.setVisible(false);

		protocolNumberItem = new TextItem();
		protocolNumberItem.setName("template.contractNumber");

		consignAddr = new TextItem("consignAddr"/* , "寄售地点" */);
		provider = new SelectItem("supplier.id"/* , "供应商" */);
		provider.setDisplayField("code");
		CodeRPCTool.bindData(CodeRPCTool.SUPPLIERCODE, provider);
		provider.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String id = provider.getValue().toString();
				CodeRPCTool.bindSuppliercodeData(id, linkManItem, linkWayItem);
			}
		});

		depot = new SelectItem("stockRoom.id"/* , "库房" */);
		depot.setDisplayField("stockRoomChineseName");// 显示库房中文名称列
		DataSourceTool dsTool = new DataSourceTool();
		dsTool.onCreateDataSource(DSKey.S_CONSIGNRENEW,
				DSKey.S_CONSIGNRENEW_SOCKROOM, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						depot.setOptionDataSource(dataSource);
						depot.fetchData();
					}
				});
		deliveryTypeDate = new DateItem("deliveryDate"/* , "交货日期" */);
		deliveryTypeDate.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		deliveryTypeDate.setUseTextField(true);

		resean = new TextItem();
		resean.setName("resean");

		supplierRenewNum = new TextItem();
		supplierRenewNum.setName("supplierRenewNum");

		addItems2Form();

		btnGroup = new HLayout();
		btnGroup.setMembersMargin(3);
		btnGroup.setMargin(5);
		btnClose = new IButton("关闭");
		btnClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				model.openedWindow.close();
			}
		});
		btnGroup.addMember(btnClose);
		Timer timer = Utils.startAmountTimer(itemGrid, totalCount, totalPrice,
				"price");
		timer.scheduleRepeating(500);
	}

	/**
	 * 将表单元素添加到表单中
	 */
	public void addItems2Form() {

		form.setFields(item_no, protocolIdItems, protocolNumberItem,
				consignAddr, provider, linkManItem, depot, linkWayItem,
				deliveryTypeDate, deliveryTypeItem, isBuyerFreightAgentItem,
				carrierNameItem, carrierLinkManItem, transportationCodeItem,
				carrierLinkWayItem, tradeMethodItem, totalCount, totalPrice,
				resean, supplierRenewNum);
	}
}

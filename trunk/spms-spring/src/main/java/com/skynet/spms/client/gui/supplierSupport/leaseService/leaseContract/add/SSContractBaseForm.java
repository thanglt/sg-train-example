package com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract.add;

import com.google.gwt.user.client.Timer;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.base.CodeRPCTool;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressModel;
import com.skynet.spms.client.gui.supplierSupport.common.DSKey;
import com.skynet.spms.client.gui.supplierSupport.leaseService.business.SSLeaseSheetBusiness;
import com.skynet.spms.client.gui.supplierSupport.leaseService.model.SSMainModelLocator;
import com.skynet.spms.client.tools.UserTools;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.skynet.spms.client.widgets.form.fields.DicSelectItem;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 供应商合同基本信息
 * 
 * @author tqc
 * 
 */
public class SSContractBaseForm extends VLayout {
	private SSMainModelLocator modellocator = SSMainModelLocator.getInstance();
	private static final String uploadUrl = "spms/upload.do";
	private SSLeaseSheetBusiness business = new SSLeaseSheetBusiness();
	private BaseAddressModel addressModel = BaseAddressModel.getInstance();
	public static LayoutDynamicForm form = new LayoutDynamicForm();
	TextItem quantityItem;
	TextItem extendedValueTotalAmountItem;

	public SSContractBaseForm(final ListGrid lg) {
		form.setNumCols(6);
		form.setCellPadding(2);
		final DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource(DSKey.S_LEASECONTRACT,
				DSKey.S_LEASECONTRACT_DS, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {

						form.setDataSource(dataSource);
						TextItem rqid = new TextItem();
						rqid.setName("rqId");
						rqid.setValue(modellocator.leaseInstructListGrid
								.getSelectedRecord().getAttribute("id"));
						rqid.setVisible(false);

						TextItem customerLeaseContractId = new TextItem();
						customerLeaseContractId
								.setName("customerLeaseContractId");
						customerLeaseContractId
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord().getAttribute(
												"m_LeaseContractTemplate.id"));
						customerLeaseContractId.setVisible(false);

						TextItem contratNumberItem = new TextItem();
						contratNumberItem.setName("contractNumber");
						contratNumberItem.setTitle("合同编号");
						contratNumberItem.setValue("系统自动生成");
						contratNumberItem.setDisabled(true);

						// 租赁申请号
						TextItem codeItem = new TextItem();
						codeItem.setName("LeaseRequisitionNumber");
						codeItem.setDisabled(true);
						codeItem.setTitle("申请指令号");
						codeItem.setValue(modellocator.leaseInstructListGrid
								.getSelectedRecord()
								.getAttribute("orderNumber"));

						// 优先级
						DicSelectItem priorityItem = new DicSelectItem();
						priorityItem.setTitle("优先级");
						priorityItem.setName("m_Priority");
						priorityItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.m_Priority"));
						// 供应商
						final SelectItem supplierItem = new SelectItem();
						supplierItem.setTitle("供应商");
						supplierItem.setName("seller.id");
						CodeRPCTool.bindData(CodeRPCTool.SUPPLIERCODE,
								supplierItem);

						// 联系人
						final TextItem linkManItem = new TextItem();
						linkManItem.setName("linkman");
						linkManItem.setTitle("联系人");

						// 飞机机尾号
						DicSelectItem planeLastNumberItem = new DicSelectItem();
						planeLastNumberItem.setName("aiNumber");
						planeLastNumberItem.setTitle("飞机机尾号");
						Utils
								.setAirIdentificationNumberItemDS(planeLastNumberItem);
						planeLastNumberItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.aiNumber"));

						// 联系方式
						final TextItem linkWayItem = new TextItem();
						linkWayItem.setName("contactInformation");
						linkWayItem.setColSpan(3);
						linkWayItem.setTitle("联系方式");

						// 交货方式
						DicSelectItem deliveryTypeItem = new DicSelectItem();
						deliveryTypeItem.setName("deliveryType");
						deliveryTypeItem.setTitle("交货方式");
						deliveryTypeItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.m_DeliveryType"));

						// 由供应商指定运代
						CheckboxItem isBuyerFreightAgentItem = new CheckboxItem();
						isBuyerFreightAgentItem.setName("sellerFreightAgent");
						isBuyerFreightAgentItem.setColSpan(3);
						isBuyerFreightAgentItem.setTitle("由供应商指定运代");

						// 运输方式
						DicSelectItem transportationCodeItem = new DicSelectItem();
						transportationCodeItem.setName("m_TransportationCode");
						transportationCodeItem.setTitle("运输方式");
						transportationCodeItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.m_TransportationCode"));

						// 运代商
						final SelectItem carrierNameItem = new SelectItem();
						carrierNameItem.setName("m_CarrierName.id");
						carrierNameItem.setTitle("运代商");
						carrierNameItem.setDisabled(true);
						CodeRPCTool.bindData(CodeRPCTool.CARRIERNAMECODE,
								carrierNameItem);
						// 运代商联系人
						final TextItem carrierLinkManItem = new TextItem();
						carrierLinkManItem.setName("appointForwarderLinkman");
						carrierLinkManItem.setTitle("联系人");
						carrierLinkManItem.setDisabled(true);

						// 贸易方式
						SelectItem tradeMethodItem = new SelectItem();
						tradeMethodItem.setName("m_TradeMethods");
						tradeMethodItem.setTitle("贸易方式");
						tradeMethodItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.m_TradeMethods"));

						// 运代商联系方式
						final TextItem carrierLinkWayItem = new TextItem();
						carrierLinkWayItem.setName("appointForwarderContact");
						carrierLinkWayItem.setTitle("联系方式");
						carrierLinkWayItem.setDisabled(true);
						carrierLinkWayItem.setColSpan(3);
						carrierNameItem.addChangedHandler(new ChangedHandler() {
							public void onChanged(ChangedEvent event) {
								String id = carrierNameItem.getValueAsString();
								CodeRPCTool.bindCarrierData(id,
										carrierLinkManItem, carrierLinkWayItem);
							}
						});
						// 判断是否选中复选框
						isBuyerFreightAgentItem
								.addChangedHandler(new ChangedHandler() {
									public void onChanged(ChangedEvent event) {
										carrierNameItem
												.setDisabled(!((Boolean) event
														.getValue()));
										carrierLinkManItem
												.setDisabled(!((Boolean) event
														.getValue()));
										carrierLinkWayItem
												.setDisabled(!((Boolean) event
														.getValue()));
									}
								});

						// GTA协议
						final DicSelectItem enterpriseGTAItem = new DicSelectItem();
						enterpriseGTAItem.setName("enterpriseIds");
						enterpriseGTAItem.setMultiple(true);
						enterpriseGTAItem.setTitle("GTA协议");
						supplierItem.addChangedHandler(new ChangedHandler() {
							public void onChanged(ChangedEvent event) {
								String id = supplierItem.getValueAsString();
								CodeRPCTool.bindSuppliercodeData(id,
										linkManItem, linkWayItem,
										enterpriseGTAItem);
							}
						});

						// 支付方式
						DicSelectItem paymentItem = new DicSelectItem();
						paymentItem.setName("m_Payment");
						paymentItem.setTitle("支付方式");
						paymentItem.setValue(modellocator.leaseInstructListGrid
								.getSelectedRecord().getAttribute(
										"m_LeaseContractTemplate.m_Payment"));

						// 支付说明
						TextItem paymentExplainItem = new TextItem();
						paymentExplainItem.setName("paymentExplain");
						paymentExplainItem.setColSpan(3);
						paymentExplainItem.setTitle("支付说明");
						paymentExplainItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.paymentExplain"));

						// 特殊条款
						TextAreaItem extraProvisionItem = new TextAreaItem();
						extraProvisionItem.setName("extraProvision");
						extraProvisionItem.setColSpan(3);
						extraProvisionItem.setTitle("特殊条款");
						extraProvisionItem
								.setTitleVAlign(VerticalAlignment.TOP);
						extraProvisionItem.setHeight(50);
						extraProvisionItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.extraProvision"));

						// 备注
						TextAreaItem remarkTextItem = new TextAreaItem();
						remarkTextItem.setName("remarkText");
						remarkTextItem.setTitle("备注");
						remarkTextItem.setTitleVAlign(VerticalAlignment.TOP);
						remarkTextItem.setHeight(50);
						remarkTextItem
								.setValue(modellocator.leaseInstructListGrid
										.getSelectedRecord()
										.getAttribute(
												"m_LeaseContractTemplate.remarkText"));

						// 数量总计
						quantityItem = new TextItem();
						quantityItem.setName("quantity");
						quantityItem.setTitle("项数总计");
						quantityItem.setDisabled(true);

						// 金额总计
						extendedValueTotalAmountItem = new TextItem();
						extendedValueTotalAmountItem
								.setName("extendedValueTotalAmount");
						extendedValueTotalAmountItem.setTitle("金额总计");
						extendedValueTotalAmountItem.setDisabled(true);

						// 制定人
						TextItem makeByItem = new TextItem();
						makeByItem.setName("makeBy");
						makeByItem.setTitle("制定人");
						makeByItem.setValue(UserTools.getCurrentUserName());
						makeByItem.setDisabled(true);

						form.setFields(rqid, customerLeaseContractId,
								contratNumberItem, codeItem, priorityItem,
								supplierItem, linkManItem, planeLastNumberItem,
								linkWayItem, deliveryTypeItem,
								isBuyerFreightAgentItem,
								transportationCodeItem, carrierNameItem,
								carrierLinkManItem, tradeMethodItem,
								carrierLinkWayItem, enterpriseGTAItem,
								paymentItem, paymentExplainItem,
								extraProvisionItem, remarkTextItem, makeByItem,
								quantityItem, extendedValueTotalAmountItem);

						Timer timer = Utils.startAmountTimer(lg, quantityItem,
								extendedValueTotalAmountItem, "price");
						timer.scheduleRepeating(500);

					}
				});

		this.addMember(form);
		/** 操作按钮 **/
		HLayout btnGroup = new HLayout();
		btnGroup.setMembersMargin(3);
		btnGroup.setMargin(5);
		btnGroup.setLayoutLeftMargin(10);

		IButton btnSave = new IButton("保存");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.saveData(new DSCallback() {
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						String leaseContractId = response.getData()[0]
								.getAttribute("id");

						modellocator.SSLeaseContractId = leaseContractId;
						addressModel.addr_sheetId = leaseContractId;
						SC.say("保存成功");
						modellocator.saveBtn.setDisabled(false);
						modellocator.defaultUploader.setVisible(true);
						modellocator.defaultUploader.setServletPath(uploadUrl
								+ "?uuid=" + modellocator.SSLeaseContractId);
						business
								.refeshListGrid(modellocator.ssleaseContractListGrid);

					}
				});
			}
		});

		btnGroup.addMember(btnSave);
		this.addMember(btnGroup);
	}
}

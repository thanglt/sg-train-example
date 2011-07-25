package com.skynet.spms.client.gui.customerService.leaseService.leasecontract.add;

import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.customerService.common.DSKey;
import com.skynet.spms.client.gui.customerService.i18n.I18n;
import com.skynet.spms.client.gui.customerService.leaseService.model.MainModelLocator;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 发货地址信息配置
 * 
 * @author fl
 * 
 */
public class AddressForm extends VLayout {

	public MainModelLocator model = MainModelLocator.getInstance();

	private I18n ui = new I18n();

	public DynamicForm form = new DynamicForm();

	public AddressForm() {
		form.setNumCols(4);
		// 初始化地址添加数据源
		final DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource(DSKey.C_LEASECONTRACT,
				DSKey.C_LEASEADDRESS_DS, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						form.setDataSource(dataSource);
						model.addressDataSource = dataSource;
					}
				});

		build();
	}

	private void build() {
		final HiddenItem contractId = new HiddenItem();
		contractId.setName("uuid");

		TextItem shippingAdressItem = new TextItem();
		shippingAdressItem.setTitle("发货地址");
		shippingAdressItem.setWidth("100%");
		shippingAdressItem.setName("shippingAddr");

		TextItem consigneeAdressItem = new TextItem();
		consigneeAdressItem.setTitle("收货地址");
		consigneeAdressItem.setWidth("100%");
		consigneeAdressItem.setName("consigneeAddr");

		TextItem consignorItem = new TextItem();
		consignorItem.setTitle("默认发货人");
		consignorItem.setWidth("100%");
		consignorItem.setName("shippingMan");

		TextItem recipientItem = new TextItem();
		recipientItem.setTitle("默认收货人");
		recipientItem.setWidth("100%");
		recipientItem.setName("consigneeMan");

		TextItem shippingAddressAndPostCodeItem = new TextItem();
		shippingAddressAndPostCodeItem.setTitle("发货地址邮编");
		shippingAddressAndPostCodeItem.setWidth("100%");
		shippingAddressAndPostCodeItem.setName("shippingZipCode");

		TextItem consignorAddressAndPostCodeItem = new TextItem();
		consignorAddressAndPostCodeItem.setTitle("收货地址邮编");
		consignorAddressAndPostCodeItem.setWidth("100%");
		consignorAddressAndPostCodeItem.setName("invocieZipCode");

		TextItem recipeInvoiceAdressItem = new TextItem();
		recipeInvoiceAdressItem.setTitle("收发票地址");
		recipeInvoiceAdressItem.setWidth("100%");
		recipeInvoiceAdressItem.setName("invocieAddr");
		recipeInvoiceAdressItem.setColSpan(2);

		TextItem recipeItem = new TextItem();
		recipeItem.setTitle("默认收取人");
		recipeItem.setWidth("100%");
		recipeItem.setName("invocieMan");
		recipeItem.setColSpan(2);

		TextItem recipeInvoiceAddressAndPostCodeItem = new TextItem();
		recipeInvoiceAddressAndPostCodeItem.setTitle("收取地址邮编");
		recipeInvoiceAddressAndPostCodeItem.setWidth("100%");
		recipeInvoiceAddressAndPostCodeItem.setName("consigneeZipCode");

		form.setFields(contractId, shippingAdressItem, consigneeAdressItem,
				consignorItem, recipientItem, shippingAddressAndPostCodeItem,
				consignorAddressAndPostCodeItem, recipeInvoiceAdressItem,
				recipeItem, recipeInvoiceAddressAndPostCodeItem);
		this.addMember(form);

		HLayout btnGroup = new HLayout();
		btnGroup.setMembersMargin(3);
		btnGroup.setMargin(5);

		final IButton btnSave = new IButton(ui.getB().btnSaveAdd());
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (model.LeaseContractId != null) {
					contractId.setValue(model.LeaseContractId);
					form.saveData(new DSCallback() {
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							SC.say(ui.getB().msgAddOpSuccess());
							btnSave.setDisabled(true);
						}
					});
				} else {
					SC.warn("请先保存合同基本信息");
				}

			}
		});

		btnGroup.addMember(btnSave);
		this.addMember(btnGroup);
	}

}

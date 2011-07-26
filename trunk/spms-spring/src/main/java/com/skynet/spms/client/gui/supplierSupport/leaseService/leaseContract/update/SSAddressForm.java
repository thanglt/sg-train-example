package com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract.update;

import com.skynet.spms.client.gui.customerService.i18n.I18n;
import com.skynet.spms.client.gui.supplierSupport.leaseService.model.SSMainModelLocator;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
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
public class SSAddressForm extends VLayout {

	public SSMainModelLocator model = SSMainModelLocator.getInstance();

	private I18n ui = new I18n();

	public DynamicForm form = new DynamicForm();

	public SSAddressForm() {
		form.setNumCols(4);
		form.setDataSource(model.SSaddressDataSource);
		Criteria criteria = new Criteria();
		criteria.setAttribute("uuid", model.SSLeaseContractId);
		form.fetchData(criteria);
		build();
	}

	private void build() {
		final HiddenItem contractId = new HiddenItem();
		contractId.setName("uuid");
		TextItem addrId = new TextItem();
		addrId.setName("id");
		addrId.setVisible(false);

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

		HLayout btnGroup = new HLayout();
		btnGroup.setMembersMargin(3);
		btnGroup.setMargin(5);

		form.setFields(contractId, shippingAdressItem, consigneeAdressItem,
				consignorItem, recipientItem, shippingAddressAndPostCodeItem,
				consignorAddressAndPostCodeItem, recipeInvoiceAdressItem,
				recipeItem, recipeInvoiceAddressAndPostCodeItem, addrId);
		this.addMember(form);

		final IButton btnSave = new IButton(ui.getB().btnSaveAdd());
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (model.SSLeaseContractId != null) {
					contractId.setValue(model.SSLeaseContractId);
					form.saveData(new DSCallback() {
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							SC.say(ui.getB().msgAddOpSuccess());
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

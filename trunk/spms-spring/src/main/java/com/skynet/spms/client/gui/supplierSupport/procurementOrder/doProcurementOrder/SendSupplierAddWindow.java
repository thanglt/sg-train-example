package com.skynet.spms.client.gui.supplierSupport.procurementOrder.doProcurementOrder;

import com.skynet.spms.client.gui.base.BaseWindow;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
/**
 * 添加供应商
 * @author Tony FANG
 *
 */
public class SendSupplierAddWindow extends BaseWindow {

	private FilterListGrid canSendSupplierListGrid;//可发送的供应商列表
	private FilterListGrid waitSendSupllerListGrid;//等待提交的供应商列表

	public SendSupplierAddWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
		super(windowTitle, isMax, srcRect, listGrid, iconUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {
		// TODO Auto-generated method stub
		/**主面板**/
		VLayout vmain = new VLayout();
		vmain.setWidth100();
		vmain.setHeight100();
		
		/**供应商选择面板**/
		VLayout listLayout = new VLayout();
		listLayout.setHeight("90%");
		listLayout.setWidth100();
		
		//可发送供应商
		VLayout oneLayout = new VLayout();
		oneLayout.setMargin(20);
		oneLayout.setMembersMargin(5);
		Label lb1 = new Label("可发送供应商");
		lb1.setHeight("20");
		oneLayout.addMember(lb1);
		buildCanSendSupplierListGrid();
		oneLayout.addMember(canSendSupplierListGrid);
		listLayout.addMember(oneLayout);

		//中间操作按钮
		listLayout.addMember(getOperationLayout());
		
		VLayout twoLayout =new VLayout();
		twoLayout.setMargin(20);
		twoLayout.setMembersMargin(5);
		//发货供应商
		Label lb2 = new Label("可发送供应商");
		lb2.setHeight("20");
		twoLayout.addMember(lb2);
		buildWaitSendSupllerListGrid();
		twoLayout.addMember(waitSendSupllerListGrid);
		listLayout.addMember(twoLayout);

		//提交按钮
		VLayout btnsLayout = new VLayout();
		btnsLayout.setLayoutMargin(20);
		btnsLayout.setHeight("10%");
		btnsLayout.setWidth100();
		btnsLayout.addMember(getSubmitLayout());
		btnsLayout.setBorder("1px solid #E8E8E8");
		
		vmain.addMember(listLayout);
		vmain.addMember(btnsLayout);
		return vmain;
	}

	/**
	 * 构建可发送的供应商列表
	 */
	private void buildCanSendSupplierListGrid() {
		canSendSupplierListGrid = new FilterListGrid();
		canSendSupplierListGrid.setHeight("110");
		canSendSupplierListGrid.setAutoFetchData(true);
		canSendSupplierListGrid
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		ListGridField field1 = new ListGridField("field1", "供应商编号");

		ListGridField field2 = new ListGridField("field2", "供应商名称");

		ListGridField field3 = new ListGridField("field3", "供应商等级");

		ListGridField field4 = new ListGridField("field4", "联系人");

		ListGridField field5 = new ListGridField("field5", "联系方式");

		canSendSupplierListGrid.setFields(field1, field2, field3, field4,
				field5);

		canSendSupplierListGrid.setDataSource(Utils.getXmlDataSource());
	}

	/**
	 * 构建等待提交的供应商列表
	 */
	private void buildWaitSendSupllerListGrid() {
		waitSendSupllerListGrid = new FilterListGrid();
		waitSendSupllerListGrid.setHeight("110");
		waitSendSupllerListGrid
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		waitSendSupllerListGrid.setAutoFetchData(true);

		ListGridField field1 = new ListGridField("field1", "供应商编号");

		ListGridField field2 = new ListGridField("field2", "供应商名称");

		ListGridField field3 = new ListGridField("field3", "联系人");

		ListGridField field4 = new ListGridField("field4", "联系方式");

		waitSendSupllerListGrid.setFields(field1, field2, field3, field4);

		waitSendSupllerListGrid.setDataSource(Utils.getXmlDataSource());
	}

	/**
	 * 中间表单的操作按钮
	 * @return
	 */
	private HLayout getOperationLayout(){
		HLayout h = new HLayout();
		h.setWidth100();
		h.setMembersMargin(5);
		h.setAlign(Alignment.CENTER);

		Button addBtn = new Button("选择加入");
		addBtn.setPadding(6);
		addBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ListGridRecord[] lgr = waitSendSupllerListGrid.getRecords();

				String oldSIItemId;
				String newSIItemId;
				ListGridRecord newLGR = canSendSupplierListGrid.getSelectedRecord();

				int lgrLenght = lgr.length;

				boolean ifExist = false;
//				for (int i = 0; i < lgrLenght; i++) {
//					oldSIItemId = lgr[i].getAttribute("id");
//					newSIItemId = newLGR.getAttribute("id");
//					if (oldSIItemId.equals(newSIItemId)) {
//						ifExist = true;
//						break;
//					}
//				}

				if (ifExist) {
					SC.say("警告", "抱歉，不能重复添加");
				} else {
					waitSendSupllerListGrid.addData(newLGR);
				}

			}
		});

		Button delBtn = new Button("移除");
		delBtn.setPadding(6);
		delBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				waitSendSupllerListGrid.removeData(waitSendSupllerListGrid.getSelectedRecord());
			}
		});

		Button allHGBtn = new Button("所有合格供应商");
		allHGBtn.setPadding(6);
		allHGBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		
		Button canSendBtn = new Button("可发送供应商");
		canSendBtn.setPadding(6);
		canSendBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});

		h.addMember(addBtn);
		h.addMember(delBtn);
		h.addMember(allHGBtn);
		h.addMember(canSendBtn);
		return h;
	}
	
	/**
	 * 表单提交操作按钮
	 * @return
	 */
	private HLayout getSubmitLayout() {
		HLayout h = new HLayout();
		h.setWidth100();
		h.setMembersMargin(5);
		Button saveBtn = new Button("保存");
		saveBtn.setPadding(6);
		saveBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});

		Button cancelBtn = new Button("取消");
		cancelBtn.setPadding(6);
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		Button helpBtn = new Button("帮助");
		helpBtn.setPadding(6);
		helpBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});

		h.addMember(saveBtn);
		h.addMember(cancelBtn);
		h.addMember(helpBtn);
		return h;
	}

}

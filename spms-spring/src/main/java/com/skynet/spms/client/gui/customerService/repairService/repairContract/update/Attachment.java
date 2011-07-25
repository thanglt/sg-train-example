package com.skynet.spms.client.gui.customerService.repairService.repairContract.update;

import com.skynet.spms.client.gui.customerService.commonui.AttachmentCanvas;
import com.skynet.spms.client.gui.customerService.repairService.repairContract.ContractModelLocator;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Attachment extends VLayout {

	ContractModelLocator model = ContractModelLocator.getInstance();

	public Attachment() {

		addMember(new AttachmentCanvas(model.repairContractListGrid
				.getSelectedRecord().getAttribute("id")));

		Label label = new Label(
				"<b>提示</b>:<font color='red'>确认上传了《客户送修确认函附件》后，合同方可提交终审。</font>");
		label.setHeight100();
		label.setPadding(5);
		label.setValign(VerticalAlignment.TOP);

		final IButton makeSureBtn = new IButton("确认");

		final IButton canelBtn = new IButton("取消");

		makeSureBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.ask("您确认上传了客户送修确认函吗?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value) {
							Record record = model.repairContractListGrid
									.getSelectedRecord();
							record.setAttribute("uploadedCusAttach", 1);
							Integer verison = record
									.getAttributeAsInt("version");
							if (verison==null) {
								verison = 0;
							}
							verison = verison + 1;
							record.setAttribute("verison", verison);
							model.repairContractListGrid.updateData(record,
									new DSCallback() {
										public void execute(
												DSResponse response,
												Object rawData,
												DSRequest request) {
											SC.say("确认成功");
											makeSureBtn.setDisabled(true);
											canelBtn.setDisabled(false);
										}
									});
						}
					}
				});
			}
		});

		canelBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SC.ask("您取消上传了客户送修确认函吗?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value) {
							Record record = model.repairContractListGrid
									.getSelectedRecord();
							record.setAttribute("uploadedCusAttach", 0);
							Integer verison = record
									.getAttributeAsInt("version");
							if (verison==null) {
								verison = 0;
							}
							verison = verison + 1;
							record.setAttribute("verison", verison);
							model.repairContractListGrid.updateData(record,
									new DSCallback() {

										public void execute(
												DSResponse response,
												Object rawData,
												DSRequest request) {
											SC.say("取消成功");
											makeSureBtn.setDisabled(false);
											canelBtn.setDisabled(true);
										}
									});
						}
					}
				});
			}
		});

		controller(makeSureBtn, canelBtn);

		Window window = new Window();
		window.setTitle("客户送修确认函面板");
		window.setWidth(350);
		window.setHeight(180);
		window.setCanDragReposition(true);
		window.setCanDragResize(true);
		window.setMinimized(false);
		window.setAlign(Alignment.CENTER);
		window.addItem(label);

		HLayout btnWrap = new HLayout();
		btnWrap.addMember(makeSureBtn);
		btnWrap.addMember(canelBtn);

		window.addItem(btnWrap);

		addMember(window);

	}

	private void controller(IButton makeSureBtn, IButton canelBtn) {

		Record record = model.repairContractListGrid.getSelectedRecord();

		Integer uploadedCusAttach = record
				.getAttributeAsInt("uploadedCusAttach");

		if (uploadedCusAttach == null || uploadedCusAttach == 0) {
			makeSureBtn.setDisabled(false);
			canelBtn.setDisabled(true);
		} else {
			makeSureBtn.setDisabled(true);
			canelBtn.setDisabled(false);
		}
	}

}

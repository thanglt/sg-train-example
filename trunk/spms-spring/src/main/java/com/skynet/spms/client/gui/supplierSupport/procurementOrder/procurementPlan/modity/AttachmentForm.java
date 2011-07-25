package com.skynet.spms.client.gui.supplierSupport.procurementOrder.procurementPlan.modity;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;

import com.google.gwt.core.client.GWT;
import com.skynet.spms.client.gui.customerService.commonui.BaseListGrid;
import com.skynet.spms.client.gui.customerService.i18n.MyUploadI18nConstants;
import com.skynet.spms.client.gui.supplierSupport.procurementOrder.procurementPlan.model.MainModelLocator;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 采购计划修改附件
 * 
 * @author fl
 * 
 */
public class AttachmentForm extends VLayout {

	MyUploadI18nConstants i18n = GWT.create(MyUploadI18nConstants.class);
	private static final String uploadUrl = "spms/upload.do";
	public MainModelLocator model = MainModelLocator.getInstance();
	private final BaseListGrid attachmentGrid;
	String partPlanNeedOrderId = model.procurementPlanListGrid
			.getSelectedRecord().getAttribute("id");
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				refresh();
			}
		}

	};

	private IUploader.OnCancelUploaderHandler onCancelUploaderHandler = new IUploader.OnCancelUploaderHandler() {
		public void onCancel(IUploader uploader) {
			if (uploader.getStatus() == Status.CANCELED) {
				refresh();
			}
		}
	};

	private IUploader.OnStatusChangedHandler onStatusChangedHandler = new IUploader.OnStatusChangedHandler() {
		public void onStatusChanged(IUploader uploader) {
			if (uploader.getStatus() == Status.DELETED) {
				refresh();
			}
		}
	};

	public AttachmentForm() {
		// 构建表格
		SectionStack sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setHeight(230);
		SectionStackSection section = new SectionStackSection("采购附件");
		section.setCanCollapse(false);
		section.setExpanded(true);
		attachmentGrid = new BaseListGrid() {
			@Override
			public void drawGrid() {

				ListGridField titleField = new ListGridField("title", "标题");
				ListGridField descriptionField = new ListGridField(
						"description", "描述");
				ListGridField fillNameField = new ListGridField("fileName",
						"文件名");
				ListGridField modifyDateField = new ListGridField("modifyDate",
						"最后修改时间");
				modifyDateField
						.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
				ListGridField operatorField = new ListGridField("operator",
						"修改人");
				attachmentGrid.setFields(titleField,
						descriptionField, fillNameField, modifyDateField,
						operatorField);
			}
		};
		attachmentGrid.invalidateCache();
		attachmentGrid.setWidth100();
		attachmentGrid.setHeight(224);
		attachmentGrid.setShowAllRecords(true);
		attachmentGrid.setCellHeight(22);
		attachmentGrid.setCanRemoveRecords(true);
		attachmentGrid.setRemoveFieldTitle("删除");
		attachmentGrid.addRecordDropHandler(new RecordDropHandler() {
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				SC.confirm("确认删除吗", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value.equals(true)) {
							SC.say("删除成功！！");
						}
					}
				});
			}
		});
		attachmentGrid.setDataSource(model.attachmentDs);
		Criteria criteria = new Criteria();
		criteria.setAttribute("uuid", partPlanNeedOrderId);
		criteria.setAttribute("_r", String.valueOf(Math.random()));
		attachmentGrid.fetchData(criteria);
		attachmentGrid.drawGrid();

		section.setItems(attachmentGrid);
		sectionStack.setSections(section);

		VLayout bar = new VLayout();
		model.defaultUploader.setI18Constants(i18n);
		model.defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
		model.defaultUploader.addOnCancelUploadHandler(onCancelUploaderHandler);
		model.defaultUploader.addOnStatusChangedHandler(onStatusChangedHandler);
		model.defaultUploader.setFileInputPrefix("default");
		model.defaultUploader.avoidRepeatFiles(true);
		model.defaultUploader.setServletPath(uploadUrl + "?uuid="
				+ partPlanNeedOrderId);
		bar.addMember(model.defaultUploader);
		addMember(sectionStack);
		addMember(bar);

	}

	private void refresh() {
		Criteria conditions = new Criteria();
		conditions.addCriteria("uuid", partPlanNeedOrderId);
		conditions.addCriteria("_r", String.valueOf(Math.random()));
		attachmentGrid.fetchData(conditions);
	}

}

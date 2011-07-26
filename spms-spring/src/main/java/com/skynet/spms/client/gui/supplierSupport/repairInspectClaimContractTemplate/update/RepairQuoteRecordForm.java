package com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.update;

import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.customerService.commonui.AttachmentCanvas;
import com.skynet.spms.client.gui.supplierSupport.common.DSKey;
import com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.model.ModelLocator;
import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 修理记录配置
 * 
 * @author tqc
 * 
 */
public class RepairQuoteRecordForm extends VLayout {

	private DynamicForm form = new DynamicForm();

	public static final ListGrid itemDetailGrid = new ListGrid();

	private ModelLocator model = ModelLocator.getInstance();

	public static AttachmentCanvas attachmentCanvas = new AttachmentCanvas();

	private ContractAmountOper contractAmountOper;

	public ContractAmountOper getContractAmountOper() {
		return contractAmountOper;
	}

	public void setContractAmountOper(ContractAmountOper contractAmountOper) {
		this.contractAmountOper = contractAmountOper;
	}

	public RepairQuoteRecordForm() {
		DataSourceTool tool = new DataSourceTool();
		tool.onCreateDataSource(DSKey.R_EPAIRINSPECTCLAIMCONTRACTTEMPLATE,
				DSKey.R_REPAIRQUOTERECORD_DS, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						form.setDataSource(dataSource);
						build();
						Criteria criteria = new Criteria();
						criteria.setAttribute("key", "getByID");
						criteria.setAttribute("contractId",
								model.repairInsClaimContractListGrid
										.getSelectedRecord().getAttribute("id"));
						form.fetchData(criteria, new DSCallback() {
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								// 存在记录了
								if (response.getData() != null
										&& response.getData().length > 0) {
									final String repairReocrdId = response
											.getData()[0].getAttribute("id");
									model.repairReocrdId = repairReocrdId;
									initItemGird(repairReocrdId);
									// attachmentCanvas.setUuid(repairReocrdId);
									// addMember(attachmentCanvas);
								} else {// 第一次添加
									initItemGird("temp");
									// addMember(new AttachmentCanvas());
								}
							}
						});

					}
				});
	}

	private void initItemGird(final String repairReocrdId) {
		final DataSourceTool tool = new DataSourceTool();
		// 构建明细grid
		tool.onCreateDataSource(DSKey.R_EPAIRINSPECTCLAIMCONTRACTTEMPLATE,
				DSKey.R_REPAIRQUOTERECORDITEM_DS, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						itemDetailGrid.setDataSource(dataSource);
						itemDetailGrid.setCanRemoveRecords(true);
						Criteria criteria = new Criteria();
						criteria.setAttribute("key", "getByID");
						criteria.setAttribute("repairQutoReocdId",
								repairReocrdId);
						itemDetailGrid.fetchData(criteria, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								buildGrid();
							}
						});
					}
				});
	}

	private void build() {
		form.setNumCols(4);
		final HiddenItem supplierSupportContractIdHiddenItem = new HiddenItem();
		supplierSupportContractIdHiddenItem
				.setName("supplierSupportContractId");

		DateItem checkDateItem = new DateItem();
		checkDateItem.setTitle("报价日期");
		checkDateItem.setName("detectionDate");
		checkDateItem.setUseTextField(true);
		checkDateItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		SpinnerItem checkCompanyItem = new SpinnerItem();
		checkCompanyItem.setName("usefulLife");
		checkCompanyItem.setTitle("有效日期(天)");

		TextAreaItem inspectDescriptionItem = new TextAreaItem();
		inspectDescriptionItem.setColSpan(2);
		inspectDescriptionItem.setWidth("100%");
		inspectDescriptionItem.setName("repairQuotedescription");
		inspectDescriptionItem.setTitle("修理报价描述");

		form.setFields(supplierSupportContractIdHiddenItem, checkDateItem,
				checkCompanyItem, inspectDescriptionItem);

		// 创建操作按钮
		final IButton btnSave = new IButton("保存记录");
		final IButton btnItemSave = new IButton("添加明细");
		final IButton btnItemModify = new IButton("保存明细");
		final IButton btnItemCannel = new IButton("取消");

		// 按钮构建
		HLayout masterBtnGroup = new HLayout();
		masterBtnGroup.setMembersMargin(3);
		masterBtnGroup.setHeight(25);

		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// 合同主键
				String contractId = model.repairInsClaimContractListGrid
						.getSelectedRecord().getAttribute("id");
				supplierSupportContractIdHiddenItem.setValue(contractId);
				form.saveData(new DSCallback() {
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						// 为附件设置外键
						/*
						 * attachmentCanvas.setUuid(response
						 * .getData()[0].getAttribute("id"));
						 */
						model.repairReocrdId = response.getData()[0]
								.getAttribute("id");
						SC.say("保存成功");
					}
				});

			}
		});

		// 构建表格
		SectionStack sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setHeight(230);
		SectionStackSection section = new SectionStackSection("费用处理明细项");
		section.setCanCollapse(false);
		section.setExpanded(true);

		section.setItems(itemDetailGrid);
		sectionStack.setSections(section);
		masterBtnGroup.addMember(btnSave);

		HLayout itemBtnGroup = new HLayout();
		itemBtnGroup.setHeight(25);

		// 编辑键入事件
		itemDetailGrid.addEditorEnterHandler(new EditorEnterHandler() {
			public void onEditorEnter(EditorEnterEvent event) {
				// 设置外键
				itemDetailGrid.setEditValue(event.getRowNum(),
						"repairQuoteRecordId", model.repairReocrdId);
				// 设置项号
				itemDetailGrid.setEditValue(event.getRowNum(), "itemNumber",
						event.getRowNum() + 1);
			}
		});

		btnItemSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				itemDetailGrid.startEditingNew();
			}
		});

		btnItemModify.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Account item amount.
				RecordList list = itemDetailGrid.getRecordList();
				for (int i = 0; i < list.getLength(); i++) {
					Record record = list.get(i);
					String unitOfPrice = record.getAttribute("unitOfPrice");
					String quantity = record.getAttribute("quantity");
					Float amount = Float.parseFloat(unitOfPrice)
							* Float.parseFloat(quantity);
					record.setAttribute("amount", amount);
				}

				itemDetailGrid.saveAllEdits(new Function() {
					public void execute() {
						contractAmountOper
								.accountAmount(model.repairInsClaimContractListGrid
										.getSelectedRecord().getAttribute("id"));
					}
				});

			}
		});

		btnItemCannel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				itemDetailGrid.discardAllEdits();
			}
		});

		itemBtnGroup.addMember(btnItemSave);
		itemBtnGroup.addMember(btnItemModify);
		itemBtnGroup.addMember(btnItemCannel);

		addMember(form);
		addMember(masterBtnGroup);
		addMember(sectionStack);
		addMember(itemBtnGroup);
	}

	/***
	 * 构建表格
	 */
	private void buildGrid() {
		itemDetailGrid.setWidth100();
		itemDetailGrid.setHeight(224);
		itemDetailGrid.setShowAllRecords(true);
		itemDetailGrid.setCellHeight(22);
		itemDetailGrid.setCanRemoveRecords(true);
		itemDetailGrid.setCanEdit(true);
		itemDetailGrid.setModalEditing(true);
		itemDetailGrid.setEditEvent(ListGridEditEvent.CLICK);
		itemDetailGrid.setListEndEditAction(RowEndEditAction.NEXT);
		itemDetailGrid.setAutoSaveEdits(false);

		ListGridField supplierSupportContractIdFKField = new ListGridField();
		supplierSupportContractIdFKField.setName("repairQuoteRecordId");
		supplierSupportContractIdFKField.setHidden(true);

		ListGridField itemNumberField = new ListGridField();
		itemNumberField.setTitle("项号");
		itemNumberField.setName("itemNumber");
		itemNumberField.setCanEdit(false);

		ListGridField itemDescriptionField = new ListGridField();
		itemDescriptionField.setTitle("项目描述");
		itemDescriptionField.setName("itemDescription");

		FloatRangeValidator floatRangeValidator = new FloatRangeValidator();
		IntegerRangeValidator integerRangeValidator = new IntegerRangeValidator();

		ListGridField unitOfPriceField = new ListGridField();
		unitOfPriceField.setTitle("单价");
		unitOfPriceField.setName("unitOfPrice");
		unitOfPriceField.setValidators(floatRangeValidator);
		unitOfPriceField.setRequired(true);

		ListGridField quantityField = new ListGridField();
		quantityField.setTitle("数量");
		quantityField.setName("quantity");
		quantityField.setEditorType(new SpinnerItem());
		quantityField.setValidators(integerRangeValidator);
		quantityField.setRequired(true);

		ListGridField amountField = new ListGridField();
		amountField.setName("amount");
		amountField.setTitle("金额");
		amountField.setValidators(floatRangeValidator);
		amountField.setCanEdit(false);

		ListGridField internationalCurrencyCodeField = new ListGridField();
		internationalCurrencyCodeField.setTitle("币种");
		internationalCurrencyCodeField.setName("m_InternationalCurrencyCode");

		ListGridField remarkTextField = new ListGridField();
		remarkTextField.setTitle("备注");
		remarkTextField.setName("remarkText");

		itemDetailGrid.setFields(supplierSupportContractIdFKField,
				itemNumberField, itemDescriptionField, unitOfPriceField,
				quantityField, amountField, internationalCurrencyCodeField,
				remarkTextField);
	}

}

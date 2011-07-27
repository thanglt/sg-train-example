package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.datasource.HmessageDataSource;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HmessageField;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class InviteGroupWindow extends Window{

	
	@Inject
	private AppUiService auiservice;
	
	private HmessageDataSource hmDs;
	
	@Inject
	private AppStatusService aservice;
	
	private DynamicForm editorForm = new DynamicForm();
	
	private Record record;
	
	@Inject
	public InviteGroupWindow(HmessageDataSource hmDs){
		this.hmDs = hmDs;
		setWidth(600);
		setHeight(450);
		setIsModal(false);
		centerInPage();
		init();
	}
	
	public void show(String purpose,Record r){
		this.record = r;
		if("inviteGroup".equals(purpose)){
			editorForm.setValue(HmessageField.MESSAGE.getEname(), "你好：<br/>" + aservice.getSu().getLoginName() + " 邀请你加入" + r.getAttributeAsString(CommonField.NAME.getEname()) + " 群。");
			editorForm.setValue("purpose", "inviteGroup," + record.getAttributeAsString(CommonField.ID.getEname()));
		}
		show();
	}
	

	private void init(){
		VLayout layout = new VLayout(5);
        editorForm.setWidth(650);
        editorForm.setMargin(25);
        editorForm.setNumCols(2);
        editorForm.setCellPadding(5);
        editorForm.setAutoFocus(false);
        editorForm.setDataSource(hmDs);
        
        TextItem sendToItem  = new TextItem(HmessageField.RECEIVER_IDENTITY.getEname(), HmessageField.RECEIVER_IDENTITY.getCname());
        sendToItem.setRequired(true);
        
        TextItem titleItem  = new TextItem(HmessageField.TITLE.getEname(), HmessageField.TITLE.getCname());
        titleItem.setRequired(true);
        TextAreaItem msgItem = new TextAreaItem(HmessageField.MESSAGE.getEname(),HmessageField.MESSAGE.getCname());
        msgItem.setWidth(300);
        msgItem.setRequired(true);
        
        HiddenItem purposeItem = new HiddenItem("purpose");

        ButtonItem saveButton = new ButtonItem("sendItem", "发送");
        saveButton.setAlign(Alignment.CENTER);
        saveButton.setWidth(100);
        saveButton.setColSpan(4);
        saveButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				if(editorForm.validate(false)){
					editorForm.saveData(new MyDsCallback() {
						@Override
						public void afterSuccess(DSResponse response, Object rawData,
								DSRequest request) {
							editorForm.clearValues();
							auiservice.showTmpPrompt("已发送！");
						}
					});
				}
			}
		});

        editorForm.setFields(sendToItem,titleItem, msgItem,saveButton,purposeItem);
        editorForm.setColWidths(100, 200, 100, 200);
        layout.addMember(editorForm);
		addItem(layout);
	}

}

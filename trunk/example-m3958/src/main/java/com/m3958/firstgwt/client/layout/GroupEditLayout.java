package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.GroupDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.window.InviteGroupWindow;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

@Singleton
public class GroupEditLayout extends MyTreeEditLayout implements Iview{
	
	@Inject
	private GroupDataSource gds;
	
	@Inject
	private InviteGroupWindow iw;
	
	@Override
	protected void initModelForm() {
		modelForm  = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(gds);

	    TextItem cnameItem = new TextItem(CommonField.NAME.getEname(),CommonField.NAME.getCname());
	    cnameItem.setRequired(true);
	    
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);
	    
	    LengthRangeValidator lrv = new LengthRangeValidator();
	    lrv.setMin(2);
	    lrv.setMax(40);
	    
	    cnameItem.setValidators(lrv);
	    
	    final HiddenItem id = new HiddenItem("id");
	    final HiddenItem version = new HiddenItem("version");
	    
	    modelForm.setFields(cnameItem,id,version,parentIdItem);
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.GROUP_EDIT;
	}

	@Override
	protected String getModelName() {
		return GroupDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		IButton bt = new IButton("邀请用户");
		bt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				iw.show("inviteGroup",editingRecord);
			}
		});
		addMember(bt);
	}
}

package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.datasource.FeedbackDataSource;
import com.m3958.firstgwt.client.types.FeedbackField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

@Singleton
public class FeedbackEditLayout extends MySimpleEditLayout implements Iview {
	
	@Inject
	private FeedbackDataSource fds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		
	    SelectItem feedbackTypeItem = auiService.createSelectItem(FeedbackField.FEEDBACK_TYPE.getEname(),FeedbackField.FEEDBACK_TYPE.getCname(),GlobalConstants.LimitFieldName.FKLX.getName());
	    feedbackTypeItem.setRequired(true);
	    
	    SelectItem feedbackStatusItem = auiService.createSelectItem(FeedbackField.FEEDBACK_STATUS.getEname(),FeedbackField.FEEDBACK_STATUS.getCname(),GlobalConstants.LimitFieldName.FKZT.getName());
	    TextAreaItem contentItem = new TextAreaItem(FeedbackField.CONTENT.getEname(),FeedbackField.CONTENT.getCname());
	    contentItem.setColSpan(4);
	    contentItem.setWidth(450);
	    contentItem.setRequired(true);
	    TextAreaItem replyContentItem = new TextAreaItem(FeedbackField.REPLY_CONTENT.getEname(),FeedbackField.REPLY_CONTENT.getCname());
	    replyContentItem.setColSpan(4);
	    replyContentItem.setWidth(450);
		modelForm.setWidth100();
		modelForm.setHeight100();
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(fds);
	    modelForm.setFields(feedbackTypeItem,feedbackStatusItem,contentItem,replyContentItem,auiService.getCreatedAtHiddenItem(),auiService.getIdHiddenItem(),auiService.getVersionHiddenItem());
	}

	@Override
	protected String getModelName() {
		return FeedbackDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FEEDBACK_EDIT;
	}


}

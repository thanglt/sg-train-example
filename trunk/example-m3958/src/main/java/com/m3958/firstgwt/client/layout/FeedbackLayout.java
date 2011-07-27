package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FeedbackDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.FeedbackField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class FeedbackLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private FeedbackDataSource fds;

	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowFilterEditor(true);
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setDataSource(fds);
	    grid.setAutoFetchData(false);

	    ListGridField feedbackTypeField = new ListGridField(FeedbackField.FEEDBACK_TYPE.getEname(), FeedbackField.FEEDBACK_TYPE.getCname());
	    feedbackTypeField.setRequired(true);
	    ListGridField feedbackStatusField = new ListGridField(FeedbackField.FEEDBACK_STATUS.getEname(), FeedbackField.FEEDBACK_STATUS.getCname());
	    ListGridField contentField = new ListGridField(FeedbackField.CONTENT.getEname(), FeedbackField.CONTENT.getCname());
	    contentField.setRequired(true);
	    
	    ListGridField replyContentField = new ListGridField(FeedbackField.REPLY_CONTENT.getEname(), FeedbackField.REPLY_CONTENT.getCname());
	    
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),feedbackTypeField,feedbackStatusField,contentField,replyContentField,auiService.getCreatedAtField(false));
	    grid.addSelectionChangedHandler(getSelectionChangedHandler());
	    
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FEED_BACK;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.FEEDBACK_EDIT;
	}

	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.fetchData(c);
	}

}

package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.FeedbackField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class FeedbackDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Feedback";
    
    @Inject
    public FeedbackDataSource(DsErrorHandler deh) {
    	setID("feedbackDS");
    	
        DataSourceTextField feedbackTypeField = new DataSourceTextField(FeedbackField.FEEDBACK_TYPE.getEname(), FeedbackField.FEEDBACK_TYPE.getCname());
        DataSourceTextField feedbackStatusField = new DataSourceTextField(FeedbackField.FEEDBACK_STATUS.getEname(), FeedbackField.FEEDBACK_STATUS.getCname());
        DataSourceTextField contentField = new DataSourceTextField(FeedbackField.CONTENT.getEname(), FeedbackField.CONTENT.getCname());
        DataSourceTextField replayContentField = new DataSourceTextField(FeedbackField.REPLY_CONTENT.getEname(), FeedbackField.REPLY_CONTENT.getCname());
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),feedbackTypeField,feedbackStatusField,contentField,replayContentField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}  

}

package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="FEEDBACKS")
public class Feedback extends BaseModel<Feedback> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.FEEDBACK_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FEEDBACK_ID")
	protected int id;
	
	private String feedbackType;
	
	private String feedbackStatus;
	
	@Lob
	private String content;
	
	@Lob
	private String replyContent;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}



}

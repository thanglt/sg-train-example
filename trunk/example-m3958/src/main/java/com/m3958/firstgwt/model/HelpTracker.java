package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="HELP_TRACKERS",uniqueConstraints={@UniqueConstraint(columnNames={"USER_ID","HELP_MESSAGE_ID"})})
public class HelpTracker extends BaseModel<HelpTracker>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
			jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
			jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jc.setExcludes(JsonExcludeFields.HMESSAGE_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HELP_TRACKER_ID")
	protected int id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID",referencedColumnName="USER_ID")
	private User user;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="HELP_MESSAGE_ID",referencedColumnName="HELP_MESSAGE_ID")
	private HelpMessage helpMessage;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HelpMessage getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(HelpMessage helpMessage) {
		this.helpMessage = helpMessage;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	private boolean readed;

}

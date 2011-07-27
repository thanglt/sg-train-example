package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import javax.persistence.Entity;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.HelpId;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="HELP_MESSAGES",uniqueConstraints={@UniqueConstraint(columnNames={"helpId"})})
public class HelpMessage extends BaseModel<HelpMessage>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.HMESSAGE_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HELP_MESSAGE_ID")
	protected int id;
	
	
	@Enumerated(EnumType.STRING)
	private HelpId helpId;
	
	@Lob
	private String helpMessage;

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setHelpId(HelpId helpId) {
		this.helpId = helpId;
	}

	public HelpId getHelpId() {
		return helpId;
	}

}

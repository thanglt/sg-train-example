package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="MY_PROC_IDS")
public class ProcessId extends BaseModel<ProcessId>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return JSONObject.fromObject(this, jc);
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="LGB_ID")
	protected int id;
	
	
	private String processId;


	public void setProcessId(String processId) {
		this.processId = processId;
	}


	public String getProcessId() {
		return processId;
	}
	
	private String dpName;


	@Override
	public int getId() {
		return id;
	}


	@Override
	public void setId(int id) {
		this.id = id;
	}


	public String getDpName() {
		return dpName;
	}


	public void setDpName(String dpName) {
		this.dpName = dpName;
	}
	
	
}

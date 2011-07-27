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
import com.m3958.firstgwt.server.types.BelongToLgb;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="REWARDS")
public class Reward extends BaseModel<Reward> implements BelongToLgb{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.REWARD_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="REWARD_ID")
	protected int id;
	
	
	@Lob
	private String jlqk;
	
	@Lob
	private String cfqk;
	
	
	
	private String bz;
	

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setJlqk(String jlqk) {
		this.jlqk = jlqk;
	}

	public String getJlqk() {
		return jlqk;
	}

	public void setCfqk(String cfqk) {
		this.cfqk = cfqk;
	}

	public String getCfqk() {
		return cfqk;
	}


}

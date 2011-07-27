package com.m3958.firstgwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


import net.sf.json.JSONObject;

@Entity
@Table(name="TOKEN")
public class Token extends BaseModel<Token>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JSONObject toJson(){
		return JSONObject.fromObject(this);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="SHEQU_ID")
	protected int id;
	
	
	private String tokenUuid;
	
	public String getTokenUuid() {
		return tokenUuid;
	}

	public void setTokenUuid(String tokenUuid) {
		this.tokenUuid = tokenUuid;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	private String purpose;
	
	private boolean used;
	
	@Lob
	private String detail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}

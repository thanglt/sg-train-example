package com.m3958.firstgwt.model;

import net.sf.json.JSONObject;

import com.m3958.firstgwt.server.types.HasToJson;

public class ShareTarget implements HasToJson{
	private int id;
	private int rid;
	private String roleName;
	private String mainName;
	private String subName;

	
	public ShareTarget(int id, int rid, String roleName, String mainName,
			String subName) {
		super();
		this.id = id;
		this.rid = rid;
		this.roleName = roleName;
		this.mainName = mainName;
		this.subName = subName;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getMainName() {
		return mainName;
	}


	public void setMainName(String mainName) {
		this.mainName = mainName;
	}


	public String getSubName() {
		return subName;
	}


	public void setSubName(String subName) {
		this.subName = subName;
	}


	@Override
	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}
	
	

}

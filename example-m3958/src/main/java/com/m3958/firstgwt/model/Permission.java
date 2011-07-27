package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

/*
 * alter table permissions add unique key UNQ_PERMISSIONS_0 (OPERATION_ID,OBJECTIDENTITY,CLASSPERMISSION,OBJECTCLASSNAME);
 * delete from permission where operation_id=6702 and objectidentity=4853 and classpermission = 0 and objectclassname='com.m3958.firstgwt.model.Group';
 * 
 */
@Entity
@Table(name="PERMISSIONS")
public class Permission extends BaseModel<Permission> implements HasCreator{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class PermissionNamedQueries{
	}
	
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.PERMISSION_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERMISSION_ID")
	private int id;
	
	
	private String name;
	
	@ManyToMany(mappedBy="permissions",fetch=FetchType.LAZY)
	private List<Role> roles = new ArrayList<Role>();
	
	@Enumerated(EnumType.STRING)
	private ObjectOperation opCode;
	
	@Override
	public int getId(){
		return this.id;
	}
	
	@Override
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
	protected String creatorIds;
	
	@ManyToOne
	@JoinColumn(name="CREATOR_ID",referencedColumnName="USER_ID")
	protected User creator;
	
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
		creatorIds = getCreatorPath(creator);
	}

	public String getCreatorIds() {
		return creatorIds;
	}

	public void setOpCode(ObjectOperation opCode) {
		this.opCode = opCode;
	}

	public ObjectOperation getOpCode() {
		return opCode;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}
}

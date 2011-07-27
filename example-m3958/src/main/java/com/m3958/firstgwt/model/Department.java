package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;

@Entity
@Table(name="DEPARTMENTS")
public class Department extends BaseModel<Department> implements TreeModel<Department>,HasCreator,HasObjectPermission{
	
	public static class DepartmentNamedQueries{
		public final static String FIND_ALLOWED_DEPARTMENTS = "findAllowedDepartments";
	}
	

	
	private static ObjectOperation[] possibleOperations;
	
	private static ObjectRoleName[] possibleObjectRoles = new ObjectRoleName[]{ObjectRoleName.CONTENT_ADDER,ObjectRoleName.CONTENT_AUDIT,ObjectRoleName.CONTENT_EDITOR,ObjectRoleName.CONTENT_READER,ObjectRoleName.OWNER,ObjectRoleName.READER};
	
	public ObjectRoleName[] getPossibleRoleNames(){
		return possibleObjectRoles;
	}
	
	static{
		possibleOperations = ObjectOperation.values();
	}
	
	public ObjectOperation[] getPossibleOperations(){
		return possibleOperations;
	}
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
			jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
			jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jc.setExcludes(JsonExcludeFields.DEPARTMENT_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="DEPARTMENT_ID")
	protected int id;
	

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1571199719142273189L;
	
	private String name;
	
	private String bz;
	
	private boolean shequContainer;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Department parent;
	
	@OneToMany(mappedBy="department")
	private List<Shequ> shequs = new ArrayList<Shequ>();
	
	
	private String parentIds;
	
	
	public String getParentIds() {
		return parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
		parentIds = getParentPath(parent);
	}

	public List<Department> getChildren() {
		return children;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getBz() {
		return bz;
	}

	public void setShequs(List<Shequ> shequs) {
		this.shequs = shequs;
	}
	

	public List<Shequ> getShequs() {
		return shequs;
	}

	public void setShequContainer(boolean shequContainer) {
		this.shequContainer = shequContainer;
	}

	public boolean getShequContainer() {
		return shequContainer;
	}

	@OneToMany(mappedBy="parent")
	private List<Department> children = new ArrayList<Department>();
	
	public int getParentId() {
		if(parent == null){
			return SmartConstants.NONE_EXIST_MODEL_ID;
		}else{
			return parent.getId();
		}
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


	@Override
	public boolean isFolder() {
		return !children.isEmpty();
	}

	@Override
	public boolean addChildren(Department bm) {
		if(!children.contains(bm)){
			children.add((Department) bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(Department bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}
	

	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE})
	@JoinTable(name="DEPARTMENT_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	

	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE})
	@JoinTable(name="DEPARTMENT_OBJECTROLE")
	private List<Role> objectRoles = new ArrayList<Role>();
	
	public List<Permission> getObjectPermissions() {
		return objectPermissions;
	}

	public void setObjectPermissions(List<Permission> permissions) {
		this.objectPermissions = permissions;
	}
	
	
	public void createObjectPermissions(){
		ObjectPermissionUtil.createObjectPermissions(this);
	}
	
	public boolean addObjectRole(Role r){
		return ObjectPermissionUtil.addObjectRole(objectRoles, r);
	}
	
	public boolean addObjectPermission(Permission p){
		return ObjectPermissionUtil.addObjectPermission(objectPermissions, p);
	}
	
	
	public void createObjectRoles(){
		ObjectPermissionUtil.createObjectRoles(this);
	}
	
	public Role createObjectRole(ObjectRoleName orn){
		return ObjectPermissionUtil.createObjectRole(this, orn);
	}
	
	public Role getObjectRole(ObjectRoleName orn){
		return ObjectPermissionUtil.getObjectRole(this, orn);
	}
	
	public void setObjectRoles(List<Role> roles) {
		this.objectRoles = roles;
	}

	public List<Role> getObjectRoles() {
		return objectRoles;
	}


	@Override
	public String getOname() {
		return name;
	}


	@Override
	public Permission getPermission(ObjectOperation opCode) {
		return ObjectPermissionUtil.getPermission(this, opCode);
	}


	@Override
	public void setChildren(List<Department> children) {
		setChildren(children);
	}

}

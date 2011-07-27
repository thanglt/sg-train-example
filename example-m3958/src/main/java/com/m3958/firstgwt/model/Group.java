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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;

@Entity
@Table(name="GROUPS", uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
public class Group extends BaseModel<Group> implements TreeModel<Group>,HasCreator,HasRelation,HasObjectPermission{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
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
		jc.setExcludes(JsonExcludeFields.GROUP_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="GROUP_ID")
	protected int id;
	
	private String name;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="GROUP_USER")
	@OrderBy("createdAt DESC")
	private List<User> users = new ArrayList<User>();
	
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Role> roles = new ArrayList<Role>();

	public boolean addRole(Role r){
		if(r==null)return false;
		if(!roles.contains(r)){
			roles.add(r);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean dropRole(Role r){
		if(roles.contains(r)){
			roles.remove(r);
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Group parent;
	
	@OneToMany(mappedBy="parent")
	private List<Group> children = new ArrayList<Group>();
	
	private String parentIds;
	
	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		if(parent != null){
			this.parent = (Group) parent;
			this.parentIds = getParentPath(parent);
			this.persistedParentId = parent.getId();
		}else{
			this.parent = null;
			this.parentIds = "";
			this.persistedParentId = SmartConstants.NONE_EXIST_MODEL_ID;
		}
	}

	
	public String getParentIds() {
		return parentIds;
	}
	
	private int persistedParentId = SmartConstants.NONE_EXIST_MODEL_ID;
	
	public void setPersistedParentId(int pid){
		persistedParentId = pid;
	}

	public int getParentId() {
		return persistedParentId;
	}


	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
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
	public boolean addChildren(Group bm) {
		if(!children.contains(bm)){
			children.add(bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(Group bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd,String hint,ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof Role){
				if(roles.contains(bm)){
					return false;
				}else{
					roles.add((Role) bm);
					return true;
				}
			}else if(bm instanceof User){
				if(users.contains(bm)){
					return false;
				}else{
					users.add((User) bm);
					return true;
				}
			}
		}else{// is remove
			if(bm instanceof Role){			
				if(roles.contains(bm)){
					roles.remove(bm);
					return true;
				}else{
					return false;
				}
			}else if(bm instanceof User){
				if(users.contains(bm)){
					users.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="GROUP_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="GROUP_OBJECTROLE")
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
	
	public boolean addObjectRole(Role r){
		return ObjectPermissionUtil.addObjectRole(objectRoles, r);
	}
	
	public boolean addObjectPermission(Permission p){
		return ObjectPermissionUtil.addObjectPermission(objectPermissions, p);
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


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public List<User> getUsers() {
		return users;
	}


	@Override
	public void setChildren(List<Group> children) {
		setChildren(children);
	}


	@Override
	public List<Group> getChildren() {
		return children;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

}

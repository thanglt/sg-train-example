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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;


@Entity
@Table(name="JRXMLFILE")
public class JrxmlFile extends BaseModel<JrxmlFile> implements HasAttachments, TreeModel<JrxmlFile>,HasObjectPermission,HasCreator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5343457320033103336L;

	private static ObjectOperation[] possibleOperations;
	
	private static ObjectRoleName[] possibleObjectRoles = ObjectRoleName.values();
	
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
		jc.setExcludes(JsonExcludeFields.JRXML_FILE_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="JRXML_FILE_ID")
	protected int id;
	
	private String modelName;
	
	private String parentIds;
	
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="PIMG_ID",referencedColumnName="ASSET_ID")
	private Asset previewImg;
	
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private JrxmlFile parent;
	
	@OneToMany(mappedBy="parent")
	private List<JrxmlFile> children = new ArrayList<JrxmlFile>();
	
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Asset> attachments;

	
	
	private String name;
	
	public String getCname(){
		return name;
	}
	
	@Lob
	@Column(length=16777210)
	private String jrxml;
	
	@Lob
	@Column(length=16777210)
	private byte[] report;
	

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setJrxml(String jrxml) {
		this.jrxml = jrxml;
	}

	public String getJrxml() {
		return jrxml;
	}

	public void setReport(byte[] report) {
		this.report = report;
	}

	public byte[] getReport() {
		return report;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}


	public void setPreviewImg(Asset previewImg) {
		this.previewImg = previewImg;
	}

	public Asset getPreviewImg() {
		return previewImg;
	}

	public void setAttachments(List<Asset> attachments) {
		this.attachments = attachments;
	}

	public List<Asset> getAttachments() {
		return attachments;
	}
	
	public boolean addAttachment(Asset asset){
		if(attachments.contains(asset)){
			return false;
		}else{
			attachments.add(asset);
			return true;
		}
	}
	public boolean dropAttachment(Asset asset){
		if(attachments.contains(asset)){
			attachments.remove(asset);
			if(previewImg != null){
				if(previewImg.getId() == asset.getId()){
					previewImg = null;
				}
			}
			return true;
		}else{
			return false;
		}
	}


	public JrxmlFile getParent() {
		return parent;
	}

	public List<JrxmlFile> getChildren() {
		return children;
	}

	private int persistedParentId = SmartConstants.NONE_EXIST_MODEL_ID;
	
	public void setPersistedParentId(int pid){
		persistedParentId = pid;
	}

	public int getParentId() {
		return persistedParentId;
	}

	@Override
	public void setParent(JrxmlFile parent) {
		this.parent = (JrxmlFile) parent;
		parentIds = getParentPath(parent);
		
	}

	public String getParentIds() {
		return parentIds;
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
	public boolean addChildren(JrxmlFile bm) {
		if(!children.contains(bm)){
			children.add((JrxmlFile) bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(JrxmlFile bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="JRXML_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="JRXML_OBJECTROLE")
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

	public List<Role> getObjectRoles() {
		return objectRoles;
	}
	
	public boolean addObjectRole(Role r){
		return ObjectPermissionUtil.addObjectRole(objectRoles, r);
	}
	
	public boolean addObjectPermission(Permission p){
		return ObjectPermissionUtil.addObjectPermission(objectPermissions, p);
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
	public void setChildren(List<JrxmlFile> children) {
		setChildren(children);
	}

}

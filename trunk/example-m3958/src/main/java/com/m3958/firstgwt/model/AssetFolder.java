package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;

@Entity
@Table(name="FOLDERS")
@NamedQueries({
	@NamedQuery(name="findTopAssetFolderByName",
			query="SELECT af FROM AssetFolder AS af WHERE af.siteId = :siteId AND af.name = :name AND af.parent IS NULL"),
	@NamedQuery(name="findTopAssetFolder",
			query="SELECT af FROM AssetFolder af WHERE af.siteId = :siteId AND af.parent IS NULL ORDER BY af.position ASC"),
	@NamedQuery(name="findAssetFolderByNameAndParent",
			query="SELECT af FROM AssetFolder af WHERE af.siteId = :siteId AND af.name = :name AND af.parent = :parent")			
			
})
public class AssetFolder extends BaseModel<AssetFolder> implements TreeModel<AssetFolder>,HasCreator,HasObjectPermission,HasRelation,IHasSiteId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_TOP_BY_NAME = "findTopAssetFolderByName";
		public final static String FIND_TOP = "findTopAssetFolder";
		public final static String FIND_BY_NAME_PARENT = "findAssetFolderByNameAndParent";
	}
	

	
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
			jc.setExcludes(JsonExcludeFields.FOLDER_EXCLUDES);

		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FOLDER_ID")
	protected int id;
	
	private int perpage;
	
	private String name;
	
	
	private int siteId;
	
	@Transient
	private String tplName = "assetfolder";
	
	private int position;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private AssetFolder parent;
	
	@OneToMany(mappedBy="parent")
	private List<AssetFolder> children = new ArrayList<AssetFolder>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FOLDER_ASSET",
				joinColumns=
					@JoinColumn(name="FOLDERS_FOLDER_ID"),
				inverseJoinColumns=
					@JoinColumn(name="ASSETS_ASSET_ID")
		)
	@OrderBy("createdAt DESC")
	private List<Asset> assets = new ArrayList<Asset>();
	
	private String parentIds;
	
	public AssetFolder getParent() {
		return parent;
	}
	
	
	public String getUrl(){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("assetfolder",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=assetfolder&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	public String getUrl(String tplName){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("assetfolder",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=assetfolder&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}


	public void setParent(AssetFolder parent) {
		if(parent != null){
			this.parent = (AssetFolder) parent;
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
	
	public boolean hasAssets(){
		return assets.size()>0;
	}
	
	public boolean hasChildrenAssets(){
		return getChildrenAssetCount()>0;
	}
	
	public List<Asset> getChildrenAssets(int num){
		return getChildrenAssets(0, num);
	}

	public List<Asset> getChildrenAssets(int start,int num){
		if(isFolder()){
			List<Asset> ars = new ArrayList<Asset>();
			Query q = emp.get().createNamedQuery(Asset.NamedQueries.GET_FOLDER_CHILDREN_ASSETS);
			q.setParameter("folderId", "%," + this.getId() + ",%");
			q.setFirstResult(start);
			q.setMaxResults(num);
			ars =  q.getResultList();
			return ars;
		}else{
			return getAssets(start, num);
		}
	}
	
	public List<Asset> getAssets(int start,int num){
		Query q = emp.get().createNamedQuery(Asset.NamedQueries.GET_FOLDER_ASSETS);
		q.setParameter("folder", this);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}
	
	
	
	public int getChildrenAssetCount(){
		if(isFolder()){
			Query q = emp.get().createNamedQuery(Asset.NamedQueries.GET_FOLDER_CHILDREN_ASSETS_COUNT);
			q.setParameter("folderId", "%," + this.getId() + ",%");
			Long c =  (Long) q.getSingleResult();
			return c.intValue();
		}else{
			return getAssetCount();
		}
	}
	
	public int getAssetCount(){
		Query q = emp.get().createNamedQuery(Asset.NamedQueries.GET_FOLDER_ASSETS_COUNT);
		q.setParameter("folder", this);
		Long c =  (Long) q.getSingleResult();
		return c.intValue();
	}


	@Override
	public boolean addChildren(AssetFolder bm) {
		if(!children.contains(bm)){
			children.add((AssetFolder) bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(AssetFolder bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="FOLDER_OBJECTPERMISSION",
				joinColumns=
					@JoinColumn(name="FOLDER_FOLDER_ID"),
				inverseJoinColumns=
					@JoinColumn(name="OBJECTPERMISSIONS_PERMISSION_ID")
			)
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="FOLDER_OBJECTROLE",
				joinColumns=
					@JoinColumn(name="FOLDER_FOLDER_ID"),
				inverseJoinColumns=
					@JoinColumn(name="OBJECTROLES_ROLE_ID")	
					
			)
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

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public List<Asset> getAssets() {
		return assets;
	}
	
	public List<AssetFolder> getBreadCrumb(){
		List<AssetFolder> pp = new ArrayList<AssetFolder>();
		AssetFolder p = this;
		while(p!=null){
			pp.add(p);
			p = p.getParent();
		}
		Collections.reverse(pp);
		return pp;
	}

	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd,String hint,ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof Asset){
				if(assets.contains(bm)){
					return false;
				}else{
					assets.add((Asset) bm);
					return true;
				}
			}
		}else{// is remove
			if(bm instanceof Asset){			
				if(assets.contains(bm)){
					assets.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public List<AssetFolder> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<AssetFolder> children) {
		setChildren(children);
	}


	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public int getPerpage() {
		return perpage;
	}

}

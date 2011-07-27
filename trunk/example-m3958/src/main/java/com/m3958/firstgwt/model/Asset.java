package com.m3958.firstgwt.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.FileSaveTo;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.HasTags;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;

/*
 * ALTER TABLE t1 ENGINE = InnoDB
 */

@Entity
@Table(name="ASSETS")
@NamedQueries({
	@NamedQuery(name="getFolderAssets",
			query="SELECT a FROM Asset AS a WHERE :folder MEMBER OF a.folders ORDER BY a.createdAt DESC"),
	@NamedQuery(name="getFolderAssetsCount",
			query="SELECT COUNT(DISTINCT a) FROM Asset AS a WHERE :folder MEMBER OF a.folders"),
		
	@NamedQuery(name="getFolderChildrenAssets",
			query="SELECT a FROM Asset AS a,IN (a.folders) AS f WHERE f.parentIds LIKE :folderId ORDER BY a.createdAt DESC"),
	@NamedQuery(name="getFolderChildrenAssetsCount",
			query="SELECT COUNT(DISTINCT a) FROM Asset AS a,IN (a.folders) AS f WHERE f.parentIds LIKE :folderId")
})
public class Asset extends BaseModel<Asset> implements HasTags,HasCreator,HasObjectPermission,IHasSiteId{
	
	public static class NamedQueries{
		public final static String GET_FOLDER_ASSETS = "getFolderAssets";
		public final static String GET_FOLDER_ASSETS_COUNT = "getFolderAssetsCount";
		public final static String GET_FOLDER_CHILDREN_ASSETS = "getFolderChildrenAssets";
		public final static String GET_FOLDER_CHILDREN_ASSETS_COUNT = "getFolderChildrenAssetsCount";
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
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5343457320033103336L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ASSET_ID")
	protected int id;
	
	private int siteId;
	
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.ASSET_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	
	private String filePath;
	private String fileId;
	private long fileSize;
	private String mimeType;
	private String originName;
	private String extension;
	
	@ManyToMany(mappedBy="assets",fetch=FetchType.LAZY)
	private List<AssetFolder> folders = new ArrayList<AssetFolder>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Tag> tags = new ArrayList<Tag>();
 
	
	@Lob
	private String description;
	
	@Enumerated(EnumType.STRING)
	private FileSaveTo saveTo;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

	public String getFilePath() {
		return filePath;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getOriginName() {
		return originName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setSaveTo(FileSaveTo saveTo) {
		this.saveTo = saveTo;
	}

	public FileSaveTo getSaveTo() {
		return saveTo;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setFolders(List<AssetFolder> folders) {
		this.folders = folders;
	}

	public List<AssetFolder> getFolders() {
		return folders;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Tag> getTags() {
		return tags;
	}

	@Override
	public boolean addTag(Tag tag) {
		if(tags.contains(tag)){
			return false;
		}else{
			tags.add(tag);
			return true;
		}
	}

	@Override
	public boolean dropTag(Tag tag) {
		if(tags.contains(tag)){
			tags.remove(tag);
			return true;
		}else{
			return false;
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
	
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="ASSET_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="ASSET_OBJECTROLE")
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
	
	
	public boolean addObjectRole(Role r){
		return ObjectPermissionUtil.addObjectRole(objectRoles, r);

	}
	
	public boolean addObjectPermission(Permission p){
		return ObjectPermissionUtil.addObjectPermission(objectPermissions, p);
	}
	
	public void setObjectRoles(List<Role> roles) {
		this.objectRoles = roles;
	}

	public List<Role> getObjectRoles() {
		return objectRoles;
	}

	@Override
	public String getOname() {
		return originName;
	}
	
	@Override
	public Permission getPermission(ObjectOperation opCode) {
		return ObjectPermissionUtil.getPermission(this, opCode);
	}
	
	public InputStream getStream(){
		if(saveTo == null)return null;
		switch (saveTo) {
		case FILE_SYSTEM:
			
			break;
		default:
			break;
		}
		return null;
	}
	
	public InputStream getStream(String saveToPath){
		if(saveTo == null)return null;
		switch (saveTo) {
		case FILE_SYSTEM:
			File f = new File(saveToPath);
			f = new File(f,getFilePath());
			try {
				InputStream is = new FileInputStream(f);
				return is;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return null;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getExtension() {
		return extension;
	}

	public String getThumbnail() {
		if(StringUtils.isImageExt(filePath)){
			return "/asset/" + id + ".48x48" + getExtension();
		}else{
			return "";
		}
	}
	
	public String getUrl(String size){
		if(StringUtils.isImageExt(filePath)){
			if(size == null || size.isEmpty()){
				return "/asset/" + id + getExtension();
			}else{
				return "/asset/" + id + "." + size + getExtension();
			}
		}else{
			return "/asset/" + id + getExtension();
		}
	}
	
	public boolean isImg(){
		if(filePath == null)return false;
		return StringUtils.isImageExt(filePath);
	}
	
	public String getUrl(){
		return "/asset/" + id + getExtension();
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public int getSiteId() {
		return siteId;
	}

}

/*
 * 
 * | assets | CREATE TABLE `assets` (
  `ASSET_ID` int(11) NOT NULL,
  `ORIGINNAME` varchar(255) default NULL,
  `MIMETYPE` varchar(255) default NULL,
  `SAVETO` varchar(255) default NULL,
  `DESCRIPTION` text,
  `FILESIZE` int(11) default NULL,
  `FILEID` varchar(255) default NULL,
  `FILEPATH` varchar(255) default NULL,
  `CREATEDAT` datetime default NULL,
  `CREATORIDS` varchar(255) default NULL,
  `VERSION` int(11) default NULL,
  `CREATOR_ID` int(11) default NULL,
  PRIMARY KEY  (`ASSET_ID`),
  KEY `FK_ASSETS_CREATOR_ID` (`CREATOR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 |





| assets | CREATE TABLE `assets` (
  `ASSET_ID` int(11) NOT NULL,
  `FILEPATH` varchar(255) default NULL,
  `VERSION` int(11) default NULL,
  `FILEID` varchar(255) default NULL,
  `FILESIZE` bigint(20) default NULL,
  `CREATEDAT` datetime default NULL,
  `DESCRIPTION` text,
  `SAVETO` varchar(255) default NULL,
  `CREATORIDS` varchar(255) default NULL,
  `MIMETYPE` varchar(255) default NULL,
  `ORIGINNAME` varchar(255) default NULL,
  `CREATOR_ID` int(11) default NULL,
  PRIMARY KEY  (`ASSET_ID`),
  KEY `FK_ASSETS_CREATOR_ID` (`CREATOR_ID`),
  CONSTRAINT `FK_ASSETS_CREATOR_ID` FOREIGN KEY (`CREATOR_ID`) REFERENCES `users
` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
*/
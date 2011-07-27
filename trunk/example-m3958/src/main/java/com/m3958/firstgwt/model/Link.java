/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;



@Entity
@Table(name = "LINKS")
@NamedQueries({
	@NamedQuery(name="findTopLinkByName",
			query="SELECT l FROM Link AS l WHERE l.siteId = :siteId AND l.name = :name AND l.parent IS NULL"),
	@NamedQuery(name="findTopLinks",
			query="SELECT l FROM Link l WHERE l.siteId = :siteId AND l.parent IS NULL AND l.hidden = FALSE ORDER BY l.position ASC"),
	@NamedQuery(name="findLinkByNameAndParent",
			query="SELECT l FROM Link l WHERE l.siteId = :siteId AND l.name = :name AND l.parent = :parent"),
	@NamedQuery(name="findVisibleLinkChildren",
			query="SELECT l FROM Link l WHERE l.hidden = FALSE AND l.parent = :parent"),
	@NamedQuery(name="findVisibleLinkChildrenCount",
			query="SELECT COUNT(l) FROM Link l WHERE l.hidden = FALSE AND l.parent = :parent")			

})
public class Link extends BaseModel<Link> implements TreeModel<Link>,IHasSiteId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8812597083444251003L;
	
	
	
	public static class NamedQueries{
		public final static String FIND_TOP_BY_NAME = "findTopLinkByName";
		public final static String FIND_TOP = "findTopLinks";
		public final static String FIND_BY_NAME_PARENT = "findLinkByNameAndParent";
		public final static String FIND_VISIBLE_CHILDREN = "findVisibleLinkChildren";
		public final static String FIND_VISIBLE_CHILDREN_COUNT = "findVisibleLinkChildrenCount";
	}
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="LINK_ID")
	protected int id;
	
	private int position;
	
	private String name;
	
	private String linkTo;
	
	private String lurl;
	
	private String tplName;
	
	private boolean hidden;

	private int modelId;
	
	private int siteId;
	
	@Transient
	private Map<String, String> keyValueMap;
	
	@Lob
	private String keyValues;
	
	public String getValue(String key){
		if(keyValues == null || keyValues.isEmpty())return "";
		if(keyValueMap == null){
			keyValueMap = new HashMap<String, String>();
			String[] kvs = keyValues.split(",");
			for(String kv : kvs){
				String[] kk = kv.split("=");
				if(kk.length == 1){
					keyValueMap.put(kk[0], "");
				}else{
					keyValueMap.put(kk[0], kk[1]);
				}
				
			}
		}
		String s = keyValueMap.get(key); 
		return s == null ? "" : s;
	}
	
	public void setKeyValues(String keyValues) {
		this.keyValues = keyValues;
	}

	public String getKeyValues() {
		return keyValues;
	}

	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Link parent;
	
	@OneToMany(mappedBy="parent")
	@OrderBy("position ASC")
	private List<Link> children = new ArrayList<Link>();
	
	private String parentIds;
	
	public Link getParent() {
		return parent;
	}

	public void setParent(Link parent) {
		if(parent != null){
			this.parent = parent;
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
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.LINK_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}

	private String getNoFriendUrl(String obName){
		return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=" + obName + "&" + AppConstants.OBID_PARAMETER_NAME + "=" + modelId;		
	}
	
	public String getUrl(){
		if("url".equals(linkTo) || (getLurl()!=null && !getLurl().isEmpty()))
			return lurl;
		if(getSite(siteId).isSearchFriendUrl()){
			if(Section.class.getName().equals(linkTo)){
				return getFriendUrl("section",tplName,getModelId()); 
			}else if(Article.class.getName().equals(linkTo)){
				return getFriendUrl("article",tplName,getModelId());
			}else if(AssetFolder.class.getName().equals(linkTo)){
				return getFriendUrl("assetfolder",tplName,getModelId());
			}else if(Asset.class.getName().equals(linkTo)){
				return getFriendUrl("asset",tplName,getModelId());
			}else if(XinJianCat.class.getName().equals(linkTo)){
				return getFriendUrl("xjcat",tplName,getModelId());
			}else if(XinJian.class.getName().equals(linkTo)){
				return getFriendUrl("xj",tplName,getModelId());
			}
		}else{
			if(Section.class.getName().equals(linkTo)){
				return getNoFriendUrl("section"); 
			}else if(Article.class.getName().equals(linkTo)){
				return getNoFriendUrl("article");
			}else if(AssetFolder.class.getName().equals(linkTo)){
				return getNoFriendUrl("assetfolder");
			}else if(Asset.class.getName().equals(linkTo)){
				return getNoFriendUrl("asset");
			}else if(XinJianCat.class.getName().equals(linkTo)){
				return getNoFriendUrl("assetfolder");
			}else if(XinJian.class.getName().equals(linkTo)){
				return getNoFriendUrl("asset");
			}
		}
		return "";
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
	}

	public String getLinkTo() {
		return linkTo;
	}

	public void setLurl(String lurl) {
		this.lurl = lurl;
	}
	
	public String getLurl() {
		return lurl;
	}



	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getTplName() {
		return tplName;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public int getModelId() {
		return modelId;
	}
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public List<Link> getChildren() {
		return children;
	}
	
	public List<Link> getVisibleChildren() {
		Query q = emp.get().createNamedQuery(NamedQueries.FIND_VISIBLE_CHILDREN);
		q.setParameter("parent", this);
		return q.getResultList();
	}
	
	@Override
	public boolean isFolder() {
		return !children.isEmpty();
	}
	
	@Override
	public boolean addChildren(Link bm) {
		if(!children.contains(bm)){
			children.add(bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(Link bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}

	public void setChildren(List<Link> children) {
		this.children = children;
	}


	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

}

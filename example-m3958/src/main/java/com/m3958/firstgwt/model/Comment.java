package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Table;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.IHasAudit;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@Entity
@Table(name="COMMENT")
@NamedQueries({
	@NamedQuery(name="findTopCommentBySiteId",
			query="SELECT c FROM Comment AS c WHERE c.siteId = :siteId AND c.parent IS NULL AND c.audit = :audit ORDER BY c.createdAt DESC"),
	@NamedQuery(name="findTopCommentBySiteIdAndObjectId",
			query="SELECT c FROM Comment AS c WHERE c.siteId = :siteId AND c.obid = :obid AND c.parent IS NULL AND c.audit = :audit ORDER BY c.createdAt DESC"),
	@NamedQuery(name="findFlatCommentBySiteIdAndObjectId",
			query="SELECT c FROM Comment AS c WHERE c.siteId = :siteId AND c.obid = :obid AND c.audit = :audit ORDER BY c.createdAt DESC"),
	@NamedQuery(name="findCommentBySiteIdObjectIdIp",
			query="SELECT c FROM Comment AS c WHERE c.siteId = :siteId AND c.obid = :obid AND c.ip = :ip ORDER BY c.createdAt DESC")
})
public class Comment extends BaseModel<Comment> implements IHasSiteId,TreeModel<Comment>,IHasAudit,HasCreator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_TOP_COMMENT_BY_SITE_ID = "findTopCommentBySiteId";
		public final static String FIND_TOP_COMMENT_BY_SITE_ID_OBJECT_ID = "findTopCommentBySiteIdAndObjectId";
		public final static String FIND_FLAT_COMMENT_BY_SITE_ID_OBJECT_ID = "findFlatCommentBySiteIdAndObjectId";
		public final static String FIND_COMMENT_BY_SITEID_OBJECTID_IP = "findCommentBySiteIdObjectIdIp";
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="COMMENT_ID")
	protected int id;
	
	private String nickName;
	
	private String title;
	
	private String email;
	
	private boolean audit;
	
	private String ip;
	
	@Lob
	private String message;
	
	private int siteId;
	
	@Column(length=60)
	private String obname;
	
	private String obid;
	
	private int followNum;
	
	private String parentIds;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Comment parent;
	
	@ManyToOne
	@JoinColumn(name="CREATOR_ID",referencedColumnName="USER_ID")
	protected User creator;

	
	@OneToMany(mappedBy="parent")
	private List<Comment> children = new ArrayList<Comment>();

	private int persistedParentId = SmartConstants.NONE_EXIST_MODEL_ID;
	
	public void setPersistedParentId(int pid){
		persistedParentId = pid;
	}
	
	@Override
	public int getParentId() {
		return persistedParentId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJson() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.COMMENT_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}

	@Override
	public void setSiteId(int siteId) {
		this.siteId = siteId;
		
	}

	@Override
	public TreeModel<Comment> getParent() {
		return parent;
	}

	@Override
	public void setParent(Comment parent) {
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

	@Override
	public List<Comment> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	@Override
	public boolean addChildren(Comment bm) {
		if(children.contains(bm))return false;
		children.add(bm);
		return true;
	}

	@Override
	public boolean removeChildren(Comment bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}


	@Override
	public boolean isFolder() {
		return false;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}

	public int getFollowNum() {
		return followNum;
	}

	public int getSiteId() {
		return siteId;
	}

	public String getNickName() {
		if(getCreator() != null){
			return getCreator().getLoginName();
		}
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		if(getCreator() != null){
			return getCreator().getEmail();
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setObname(String obname) {
		this.obname = obname;
	}

	public String getObname() {
		return obname;
	}

	public void setObid(String obid) {
		this.obid = obid;
	}

	public String getObid() {
		return obid;
	}

	@Override
	public String getCreatorIds() {
		return "";
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;

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
@Table(name = "VOTE")
@NamedQueries({
	@NamedQuery(name="findTopVoteByName",
			query="SELECT v FROM Vote AS v WHERE v.siteId = :siteId AND v.name = :name AND v.parent IS NULL"),
	@NamedQuery(name="findTopVotes",
			query="SELECT v FROM Vote v WHERE v.siteId = :siteId AND v.parent IS NULL AND v.hidden = FALSE ORDER BY v.position ASC"),
	@NamedQuery(name="findVoteByNameAndParent",
			query="SELECT v FROM Vote v WHERE v.siteId = :siteId AND v.name = :name AND v.parent = :parent"),
	@NamedQuery(name="findChildrenVoteNum",
			query="SELECT COUNT(v.voteHits) FROM Vote AS v WHERE v.parentIds LIKE :voteId")
})
public class Vote extends BaseModel<Vote> implements TreeModel<Vote>,IHasSiteId,HasCreator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8812597083444251003L;
	
	public static class NamedQueries{
		public final static String FIND_TOP_BY_NAME = "findTopVoteByName";
		public final static String FIND_TOP = "findTopVotes";
		public final static String FIND_BY_NAME_PARENT = "findVoteByNameAndParent";
		public final static String FIND_CHILDREN_VOTE_NUM = "findChildrenVoteNum";
		
	}
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.VOTE_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}

	
	public JSONObject toJsona(){
		JsonConfig jca = new JsonConfig();
		jca.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jca.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jca.setExcludes((String[])ArrayUtils.removeElement(JsonExcludeFields.VOTE_EXCLUDES, "children"));
		return JSONObject.fromObject(this, jca);
	}
	
	public JSONObject toJsonb(){
		JsonConfig jcb = new JsonConfig();
		Object[] bexcludes = JsonExcludeFields.VOTE_EXCLUDES;
		jcb.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jcb.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		for(String s : new String[]{"children","voteNum","childrenVoteNum"}){
			bexcludes = ArrayUtils.removeElement(bexcludes, s);
		}
		jcb.setExcludes((String[]) bexcludes);
		return JSONObject.fromObject(this, jcb);
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="VOTE_ID")
	protected int id;
	
	private int position;
	
	private String name;
	
	private boolean hidden;
	
	private String tplName;
	
	private int maxSelect;
	
	private int minSelect;

	private int siteId;
	
	private String chartType; 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="VOTE_AND_HIT",joinColumns=
										@JoinColumn(name="VOTE_ID",referencedColumnName="VOTE_ID"),
										inverseJoinColumns=@JoinColumn(name="HIT_ID",referencedColumnName="VOTE_HIT_ID"))
	@OrderBy("createdAt DESC")
	private List<VoteHit> voteHits;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Vote parent;
	
	@OneToMany(mappedBy="parent")
	@OrderBy("position ASC")
	private List<Vote> children = new ArrayList<Vote>();
	
	
	@ManyToOne
	@JoinColumn(name="CREATOR_ID",referencedColumnName="USER_ID")
	protected User creator;

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	private String parentIds;
	
	public Vote getParent() {
		return parent;
	}

	public void setParent(Vote parent) {
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

	public String getUrl(){
		if(siteId == 0)return "";
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("vote",getTplName(),getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + getTplName()+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=vote&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	public String getUrl(String tplName){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("vote",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=vote&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
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

	public int getVoteNum(){
		return this.voteHits.size();
	}
	
	public int getChildrenVoteNum(){
		Query q = emp.get().createNamedQuery(NamedQueries.FIND_CHILDREN_VOTE_NUM);
		q.setParameter("voteId", "%," + getId() + ",%");
		Long l = (Long) q.getSingleResult();
		return l.intValue();
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

	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public List<Vote> getChildren() {
		return children;
	}
	
	@Override
	public boolean isFolder() {
		return !children.isEmpty();
	}
	
	@Override
	public boolean addChildren(Vote bm) {
		if(!children.contains(bm)){
			children.add(bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(Vote bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}

	public void setChildren(List<Vote> children) {
		this.children = children;
	}


	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setVoteHits(List<VoteHit> voteHits) {
		this.voteHits = voteHits;
	}
	
	public void addVoteHits(VoteHit voteHit) {
		this.voteHits.add(voteHit);
	}


	public List<VoteHit> getVoteHits() {
		return voteHits;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public void setChartType(String chartType) {
		this.chartType = chartType;
	}


	public String getChartType() {
		return chartType;
	}


	public void setTplName(String tplName) {
		this.tplName = tplName;
	}


	public String getTplName() {
		if(tplName == null || tplName.isEmpty())return "vote";
		return tplName;
	}


	public void setMaxSelect(int maxSelect) {
		this.maxSelect = maxSelect;
	}


	public int getMaxSelect() {
		return maxSelect;
	}


	public void setMinSelect(int minSelect) {
		this.minSelect = minSelect;
	}


	public int getMinSelect() {
		return minSelect;
	}


	@Override
	public String getCreatorIds() {
		return "";
	}

}

package com.m3958.firstgwt.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.XinJianField;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.server.types.IHasAudit;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.SpecialDate;

@Entity
@Table(name="XINJIAN")
@NamedQueries({
	@NamedQuery(name="findXinjianCatXinJians",
			query="SELECT x FROM XinJian AS x WHERE :xjCat MEMBER OF x.xjCats AND x.audit = :audit AND x.gongkai = :gongkai ORDER BY x.repliedAt DESC"),
	@NamedQuery(name="findSiteXinjians",
			query="SELECT x FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit AND x.gongkai = :gongkai  ORDER BY x.repliedAt DESC"),
	@NamedQuery(name="searchXinjians",
			query="SELECT x FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit AND x.gongkai = :gongkai  AND (x.title LIKE :title OR x.content LIKE :content) ORDER BY x.repliedAt DESC"),
	@NamedQuery(name="searchXinjiansByTitle",
			query="SELECT x FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit  AND x.gongkai = :gongkai AND x.title  LIKE :title ORDER BY x.repliedAt DESC"),
	@NamedQuery(name="searchXinjianssByContent",
			query="SELECT x FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit  AND x.gongkai = :gongkai AND x.content LIKE :content ORDER BY x.repliedAt DESC"),
	@NamedQuery(name="searchXinjianCount",
			query="SELECT COUNT(DISTINCT x) FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit AND x.gongkai = :gongkai AND (x.title LIKE :title OR x.content LIKE :content)"),
	@NamedQuery(name="searchXinjianTitleCount",
			query="SELECT COUNT(DISTINCT x) FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit  AND x.gongkai = :gongkai AND x.title  LIKE :title"),
	@NamedQuery(name="searchXinjianContentCount",
			query="SELECT COUNT(DISTINCT x) FROM XinJian AS x WHERE x.siteId = :siteId AND x.audit = :audit  AND x.gongkai = :gongkai  AND x.content LIKE :content"),
	@NamedQuery(name="xjcatXinjianCount",
			query="SELECT COUNT(DISTINCT x) FROM XinJian AS x WHERE :xjCat MEMBER OF x.xjCats AND x.audit = :audit AND x.gongkai = :gongkai"),
	@NamedQuery(name="findXjCatChildrenXinjian",
			query="SELECT x FROM XinJian AS x,IN (x.xjCats) AS s WHERE s.parentIds LIKE :xjCatId AND x.audit = :audit AND x.gongkai = :gongkai ORDER BY x.repliedAt DESC"),
	@NamedQuery(name="xjcatChildrenXinjianCount",
			query="SELECT COUNT(DISTINCT x) FROM XinJian AS x,IN (x.xjCats) AS s WHERE s.parentIds LIKE :xjCatId AND x.audit = :audit AND x.gongkai = :gongkai")
})
public class XinJian extends BaseModel<XinJian> implements HasAttachments,IHasSiteId,IHasAudit{

	private static final long serialVersionUID = 1L;
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.XINJIAN_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Override
	public JSONObject toJsonOne(){
		JSONObject jo = toJson();
		jo.element(XinJianField.CONTENT.getEname(), getContent());
		jo.element(XinJianField.REPLY.getEname(), getReply());
		
		JSONArray ja1 = new JSONArray();
		for(Asset a : attachments){
			ja1.add(a.toJson());
		}
		jo.element(XinJianField.ATTACHMENTS.getEname(), ja1);
		return jo;
	}
	
	public static class NamedQueries{
		public final static String FIND_XJCAT_XINJIANS = "findXinjianCatXinJians";
		public final static String FIND_XJCAT_CHILDREN_XINJIANS = "findXjCatChildrenXinjian";
		public final static String FIND_SITE_XINJIANS = "findSiteXinjians";
		public final static String SEARCH_XINJIANS = "searchXinjians";
		public final static String XJCAT_XINJIAN_COUNT = "xjcatXinjianCount";
		public final static String XJCAT_CHILDREN_XINJIAN_COUNT = "xjcatChildrenXinjianCount";
		public final static String SEARCH_XINJIANS_BY_TITLE = "searchXinjiansByTitle";
		public final static String SEARCH_XINJIANS_BY_CONTENT = "searchXinjianssByContent";
		public final static String SEARCH_XINJIAN_COUNT = "searchXinjianCount";
		public final static String SEARCH_XINJIAN_TITLE_COUNT = "searchXinjianTitleCount";
		public final static String SEARCH_XINJIAN_CONTENT_COUNT = "searchXinjianContentCount";		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ADDRESS_ID")
	protected int id;
	
	private int siteId;
	
	private boolean audit;
	
	private String xingming;
	
	private String xingbie;
	
	@Temporal(TemporalType.DATE)
	private Date shengri;
	
	private String shouji;
	
	private String email;
	
	private String dianhua;
	
	private String dizhi;
	
	private String leixing;
	
	private String youbian;
	
	private String title;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private String content;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private String reply;
	
	private String baomima;
	
	private boolean gongkai;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date repliedAt;
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Asset> attachments = new ArrayList<Asset>();
	
	@ManyToMany(mappedBy="xinjians",fetch=FetchType.LAZY)
	private List<XinJianCat> xjCats = new ArrayList<XinJianCat>();


	private String tplName;

	
	public String getUrl(){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("xinjian",getTplName(),getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + getTplName()+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=xinjian&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	public String getUrl(String tplName){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("xinjian",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=xinjian&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public int getSiteId(){
		return siteId;
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
			return true;
		}else{
			return false;
		}
	}


	@Override
	public List<Asset> getAttachments() {
		return attachments;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setXjCats(List<XinJianCat> xjCats) {
		this.xjCats = xjCats;
	}

	public List<XinJianCat> getXjCats() {
		return xjCats;
	}
	
	public XinJianCat getXjCat(){
		if(getXjCats().size() > 0){
			return getXjCats().get(0);
		}else{
			return null;
		}
	}

	public String getXingming() {
		return xingming;
	}

	public void setXingming(String xingming) {
		this.xingming = xingming;
	}

	public Date getShengri() {
		return shengri;
	}

	public void setShengri(Date shengri) {
		this.shengri = shengri;
	}

	public String getShouji() {
		return shouji;
	}

	public void setShouji(String shouji) {
		this.shouji = shouji;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDianhua() {
		return dianhua;
	}

	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}

	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public String getYoubian() {
		return youbian;
	}

	public void setYoubian(String youbian) {
		this.youbian = youbian;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBaomima() {
		return baomima;
	}

	public void setBaomima(String baomima) {
		this.baomima = baomima;
	}

	public boolean isGongkai() {
		return gongkai;
	}

	public void setGongkai(boolean gongkai) {
		this.gongkai = gongkai;
	}

	public void setAttachments(List<Asset> attachments) {
		this.attachments = attachments;
	}

	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}

	public String getXingbie() {
		return xingbie;
	}


	public void setRepliedAt(Date repliedAt) {
		this.repliedAt = repliedAt;
	}

	public Date getRepliedAt() {
		return repliedAt;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	
	
	public String getRepliedAt(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(getRepliedAt());
	}
	
	public String getRepliedAt(String format,String tformat) {
		SpecialDate sd = new SpecialDate();
		DateFormat df;
		if(sd.isDateToday(getRepliedAt())){
			df = new SimpleDateFormat(tformat);
		}else{
			df = new SimpleDateFormat(format);
		}
		return df.format(getRepliedAt());
	}

	public String getReply() {
		return reply;
	}

	public String getTplName() {
		if(tplName == null || tplName.isEmpty())return "xinjian";
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}
	
}

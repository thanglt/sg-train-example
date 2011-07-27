package com.m3958.firstgwt.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.persistence.Basic;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.google.common.io.Files;
import com.m3958.firstgwt.client.IhasComment;
import com.m3958.firstgwt.client.layout.AttachmentBox;
import com.m3958.firstgwt.client.layout.ContentImgBox;
import com.m3958.firstgwt.client.layout.TitleImgBox;
import com.m3958.firstgwt.client.types.ArticleField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.server.types.HasTags;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.server.types.IhasContentImg;
import com.m3958.firstgwt.server.types.IhasTitleImg;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;
import com.m3958.firstgwt.utils.SpecialDate;


@Entity
@Table(name="ARTICLES")
@NamedQueries({
	@NamedQuery(name="getSectionArticle",
			query="SELECT a FROM Article AS a WHERE :section MEMBER OF a.sections AND a.audit = :audit ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="getSectionArticleCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a WHERE :section MEMBER OF a.sections AND a.audit = :audit"),

	@NamedQuery(name="getSectionsArticle",
			query="SELECT a FROM Article AS a,IN(a.sections) s WHERE s.id IN :sectionIds AND a.audit = :audit ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="getSectionsArticleCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a,IN(a.sections) s WHERE s.id IN :sectionIds AND a.audit = :audit"),
			
			
	@NamedQuery(name="searchSectionArticleOr",
			query="SELECT a FROM Article AS a WHERE :section MEMBER OF a.sections AND a.audit = :audit AND (a.title LIKE :title OR a.content LIKE :content) ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="searchSectionArticleOrCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a WHERE :section MEMBER OF a.sections AND a.audit = :audit AND (a.title LIKE :title OR a.content LIKE :content)"),						

	@NamedQuery(name="searchSectionArticleAnd",
			query="SELECT a FROM Article AS a WHERE :section MEMBER OF a.sections AND a.audit = :audit AND (a.title LIKE :title AND a.content LIKE :content) ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="searchSectionArticleAndCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a WHERE :section MEMBER OF a.sections AND a.audit = :audit AND (a.title LIKE :title AND a.content LIKE :content)"),						

			
	@NamedQuery(name="searchSectionChildrenArticlesOr",
			query="SELECT a FROM Article AS a,IN (a.sections) AS s WHERE s.parentIds LIKE :sectionId AND a.audit = :audit AND (a.title LIKE :title OR a.content LIKE :content) ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="searchSectionChildrenArticlesOrCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a,IN (a.sections) AS s WHERE s.parentIds LIKE :sectionId AND a.audit = :audit AND (a.title LIKE :title OR a.content LIKE :content)"),
					
	@NamedQuery(name="searchSectionChildrenArticlesAnd",
			query="SELECT a FROM Article AS a,IN (a.sections) AS s WHERE s.parentIds LIKE :sectionId AND a.audit = :audit AND (a.title LIKE :title AND a.content LIKE :content)ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="searchSectionChildrenArticlesAndCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a,IN (a.sections) AS s WHERE s.parentIds LIKE :sectionId AND a.audit = :audit AND (a.title LIKE :title AND a.content LIKE :content)"),
	@NamedQuery(name="findArticleByFlag",
			query="SELECT a FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND a.flag = :flag ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="findSectionArticleByFlag",
			query="SELECT a FROM Article AS a WHERE :section MEMBER OF  a.sections AND a.audit = :audit AND a.flag = :flag ORDER BY a.publishedAt DESC"),
			
	@NamedQuery(name="searchArticlesOr",
			query="SELECT a FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND (a.title LIKE :title OR a.content LIKE :content) ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="searchArticlesAnd",
			query="SELECT a FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND (a.title LIKE :title And a.content LIKE :content) ORDER BY a.publishedAt DESC"),

	@NamedQuery(name="searchArticleOrCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND (a.title LIKE :title OR a.content LIKE :content)"),
	@NamedQuery(name="searchArticleAndCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND (a.title LIKE :title AND a.content LIKE :content)"),
		
	@NamedQuery(name="getSectionChildrenArticles",
			query="SELECT a FROM Article AS a,IN (a.sections) AS s WHERE s.parentIds LIKE :sectionId AND a.audit = :audit ORDER BY a.publishedAt DESC"),
	@NamedQuery(name="getSectionChildrenArticlesCount",
			query="SELECT COUNT(DISTINCT a) FROM Article AS a,IN (a.sections) AS s WHERE s.parentIds LIKE :sectionId AND a.audit = :audit"),
	@NamedQuery(name="getSiteArticleArchives",
			query="SELECT DISTINCT FUNC('YEAR',a.publishedAt),FUNC('MONTH',a.publishedAt) FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit"),
	@NamedQuery(name="getSiteArticleArchivesInOneYear",
			query="SELECT DISTINCT FUNC('MONTH',a.publishedAt) FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND FUNC('YEAR',a.publishedAt) = :year"),			
	@NamedQuery(name="getSiteArchiveArticles",
			query="SELECT a FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit AND FUNC('YEAR',a.publishedAt) = :year AND  FUNC('MONTH',a.publishedAt) = :month"),						
	@NamedQuery(name="getSimilarArticles",
			query="SELECT DISTINCT(a) FROM Article AS a, IN(a.tags) t WHERE t.id IN (SELECT ta.id FROM Article AS ar,IN(ar.tags) ta WHERE ar = :ar AND ar.siteId = :siteId) AND a.audit = :audit AND a <> :ar AND a.siteId = :siteId ORDER BY a.publishedAt DESC")
})
public class Article extends BaseModel<Article> implements HasCreator,HasObjectPermission,HasTags,HasAttachments,HasRelation,IhasTitleImg,IhasContentImg,IHasSiteId,IhasComment{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String GET_SECTION_ARTICLE = "getSectionArticle";
		public final static String GET_SECTIONS_ARTICLE = "getSectionsArticle";
		public final static String GET_SECTION_CHILDREN_ARTICLE = "getSectionChildrenArticles";
		public final static String SECTION_ARTICLE_COUNT = "getSectionArticleCount";
		public final static String SECTIONS_ARTICLE_COUNT = "getSectionsArticleCount";
		public final static String SECTION_CHILDREN_ARTICLE_COUNT = "getSectionChildrenArticlesCount";
		public final static String FIND_FLAG_ARTICLE = "findArticleByFlag";
		public final static String FIND_SECTION_FLAG_ARTICLE = "findSectionArticleByFlag";
		public final static String SEARCH_ARTICLES_OR = "searchArticlesOr";
		public final static String SEARCH_ARTICLES_AND = "searchArticlesAnd";
		public final static String SEARCH_COUNT_OR = "searchArticleOrCount";
		public final static String SEARCH_COUNT_AND = "searchArticleAndCount";
		public final static String SEARCH_SECTION_ARTICLE_OR = "searchSectionArticleOr";
		public final static String SEARCH_SECTION_ARTICLE_AND = "searchSectionArticleAnd";
		public final static String SEARCH_SECTION_ARTICLE_COUNT_OR = "searchSectionArticleOrCount";
		public final static String SEARCH_SECTION_ARTICLE_COUNT_AND = "searchSectionArticleAndCount";
		
		public final static String SEARCH_SECTION_CHILDREN_ARTICLE_OR = "searchSectionChildrenArticlesOr";
		public final static String SEARCH_SECTION_CHILDREN_ARTICLE_AND = "searchSectionChildrenArticlesAnd";
		
		public final static String SEARCH_SECTION_CHILDREN_ARTICLE_COUNT_OR = "searchSectionChildrenArticlesOrCount";
		public final static String SEARCH_SECTION_CHILDREN_ARTICLE_COUNT_AND = "searchSectionChildrenArticlesAndCount";
		
		public final static String ARTICLE_ARCHIVES = "getSiteArticleArchives";
		
		public final static String ARCHIVE_ARTICLES = "getSiteArchiveArticles";
		
		public final static String ARTICLE_ARCHIVES_IN_ONE_YEAR = "getSiteArticleArchivesInOneYear";
		
		public final static String SIMILAR_ARTICLES = "getSimilarArticles";
	}
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.ARTICLE_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Override
	public JSONObject toJsonOne(){
		JSONObject jo = toJson();
		jo.element(ArticleField.CONTENT.getEname(), getContent());
		if(titleImg != null){
			jo.element(ArticleField.TITLE_IMG.getEname(),titleImg.toJson());
		}
		JSONArray ja = new JSONArray();
		for(Asset a : contentImgs){
			ja.add(a.toJson());
		}
		jo.element(ArticleField.CONTENT_IMGS.getEname(), ja);
		
		JSONArray ja1 = new JSONArray();
		for(Asset a : attachments){
			ja1.add(a.toJson());
		}
		String[] ts = new String[tags.size()];
		for(int i=0;i<ts.length;i++){
			ts[i] = tags.get(i).getName();
		}
		String s = StringUtils.join(ts, " ");
		jo.element(CommonField.TAG_NAMES.getEname(), s);
		jo.element(ArticleField.ATTACHMENTS.getEname(), ja1);
		return jo;
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
	
	@Transient
	private int hitNum;

	
	@OneToOne
	private Asset titleImg;
	
	@Column(length=30)
	private String flag;
	
	private int defaultSectionId;
	
	private String source;
	
	private int commentLevel;//0继承自占站点（default），1允许，2禁止
	
	@Transient
	private Map<String, String> keyValueMap;
	
	@Lob
	private String keyValues;
	
	private String protectLevel;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="ARTICLE_CONTENT_IMG")
	private List<Asset> contentImgs = new ArrayList<Asset>();
	
	public void addContentImg(Asset a){
		if(!contentImgs.contains(a)){
			contentImgs.add(a);
		}
	}
	
	public void dropContentImg(Asset a){
		if(contentImgs.contains(a)){
			contentImgs.remove(a);
		}
	}
	
	public Asset getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(Asset titleImg) {
		this.titleImg = titleImg;
	}

	public List<Asset> getContentImgs() {
		return contentImgs;
	}

	public void setContentImgs(List<Asset> contentImgs) {
		this.contentImgs = contentImgs;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ARTICLE_ID")
	protected int id;
	
	
	private String title;
	
	@Lob
	private String excerpt;
	
	@Basic(fetch=FetchType.LAZY) @Lob
	private String content;
	
	private Integer siteId;
	
	private boolean audit;
	
	private String displayAuthor;

	private String tplName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	private String metaKeywords;
	
	private String metaDescription;
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Asset> attachments = new ArrayList<Asset>();
	
	
	public List<Section> getBreadCrumb(){
		List<Section> pp = new ArrayList<Section>();
		Section p = getDefaultSection();
		if(p!=null){
			pp.add(p);
			p = p.getParent();
		}
		Collections.reverse(pp);
		return pp;
	}
	
	@PrePersist
	@PreUpdate
	public void updateUpdatedAt(){
		updatedAt = new Date();
	}
	
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


	
	public String getUrl(){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("article",getTplName(),getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + getTplName()+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=article&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	public String getUrl(String tplName){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("article",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=article&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}

	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Tag> tags = new ArrayList<Tag>();

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
	
	public Section getDefaultSection(){
		for(Section s :sections){
			if(s.getId() == defaultSectionId){
				return s;
			}
		}
		return null;
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

	

	public String getTitle() {
		return title;
	}
	
	public String getTitle(int length) {
		if(title.length() > length){
			return title.substring(0, length);
		}else{
			return title;
		}
	}
	
	public String getTitle(int length,String suffix) {
		if(title.length() > length){
			return title.substring(0, length) + suffix;
		}else{
			return title;
		}
	}
	
	public List<Article> getSimilars(int num){
		return getSimilars(0, num);
	}


	public List<Article> getSimilars(int start,int num){
		Query q = emp.get().createNamedQuery(NamedQueries.SIMILAR_ARTICLES);
		q.setParameter("ar", this);
		q.setParameter("siteId", getSiteId());
		q.setParameter("audit", true);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="ARTICLE_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="ARTICLE_OBJECTROLE")
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
		return title;
	}
	
	@Override
	public Permission getPermission(ObjectOperation opCode) {
		return ObjectPermissionUtil.getPermission(this, opCode);
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
			return true;
		}else{
			return false;
		}
	}
	
	
	@ManyToMany(mappedBy="articles",fetch=FetchType.LAZY)
	private List<Section> sections = new ArrayList<Section>();

	
	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public boolean addSection(Section s){
		if(s==null)return false;
		if(!sections.contains(s)){
			sections.add(s);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean dropGroup(Section s){
		if(sections.contains(s)){
			sections.remove(s);
			return true;
		}else{
			return false;
		}
		
	}
	
	private boolean addRelation(BaseModel bm,String hint,ErrorMessages errors){
		if(bm instanceof Asset){
			if(TitleImgBox.class.getName().equals(hint)){
				titleImg = (Asset) bm;
				return true;
			}else if(ContentImgBox.class.getName().equals(hint)){
				if(contentImgs.contains(bm)){
					return false;
				}else{
					contentImgs.add((Asset) bm);
					return true;
				}
			}else if(AttachmentBox.class.getName().equals(bm)){
				if(attachments.contains(bm)){
					return false;
				}else{
					attachments.add((Asset) bm);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean removeRelation(BaseModel bm,String hint,ErrorMessages errors){
		if(bm instanceof Asset){
			if(TitleImgBox.class.getName().equals(hint)){
				titleImg = null;
			}else if(ContentImgBox.class.getName().equals(hint)){
				if(contentImgs.contains(bm)){
					contentImgs.remove(bm);
					return true;
				}else{
					return false;
				}
			}else if(AttachmentBox.class.getName().equals(hint)){
				if(attachments.contains(bm)){
					attachments.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	private Element getDeleteField(String fieldValue){
		Element e = new Element("id");
		e.setText(fieldValue);
		return e;
		//new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	}
	
	private Element getOneField(String fieldName,String fieldValue){
		Element e = new Element("field");
		e.setAttribute("name", fieldName);
		e.setText(fieldValue);
		return e;
		//new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	
	public static List<Article> findAllBySiteId(int siteId){
		String qs = "SELECT a FROM Article AS a WHERE a.siteId = :siteId AND a.audit = true";
		Query q = emp.get().createQuery(qs);
		q.setParameter("siteId",siteId);
		List<Article> all = q.getResultList();
		return all;
	}
	
	public static void writeAllSolrCamel(int siteId){
//		String s = "<delete><query>siteId:" + siteId + "</query></delete>";
//		SiteConfigService scf = injector.getInstance(SiteConfigService.class);
//		String fs = scf.getSorlCamelDir();
//		try {
//			File f = new File(fs,"deleteSiteIndex.xml");
//			if(!f.exists())Files.createParentDirs(f);
//			Files.write(s, f , Charset.forName("UTF-8"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		for(Article a : findAllBySiteId(siteId)){
			a.writeSolrXML2File();
		}
	}
	
	private String getNotEmptyExcerpt(){
		if(getExcerpt() == null || getExcerpt().isEmpty()){
			String ss = getHtmlStripContent();
			if(ss.length() < 100){
				return ss;
			}else{
				return ss.substring(0, 100);
			}
		}else{
			return getExcerpt();
		}
	}
	
	private String getHtmlStripContent(){
		String s = getContent();
		if(s == null || s.isEmpty())return "";
		Pattern p  = Pattern.compile("<[^>]*>",Pattern.CASE_INSENSITIVE|Pattern.DOTALL|Pattern.MULTILINE);
		s = p.matcher(s).replaceAll("");
		p  = Pattern.compile("\\s+",Pattern.CASE_INSENSITIVE|Pattern.DOTALL|Pattern.MULTILINE);
		s = p.matcher(s).replaceAll("");
		p  = Pattern.compile("　+",Pattern.CASE_INSENSITIVE|Pattern.DOTALL|Pattern.MULTILINE);
		s = p.matcher(s).replaceAll("");
		return s;
	}
	
	@PostPersist
	@PostUpdate
	public void writeSolrXML2File(){
		if(isAudit()){
			SiteConfigService scf = injector.getInstance(SiteConfigService.class);
			String fs = scf.getSorlCamelDir();
			try {
				File f = new File(fs,"article" + getId() + ".xml");
				if(!f.exists())Files.createParentDirs(f);
				Files.write(toSolrXML("add"), f , Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@PostRemove
	public void deleteFromSolr(){
		SiteConfigService scf = injector.getInstance(SiteConfigService.class);
		String fs = scf.getSorlCamelDir();
		try {
			File f = new File(fs,"article" + getId() + "_delete.xml");
			if(!f.exists())Files.createParentDirs(f);
			Files.write(toSolrXML("delete"), f , Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getDeleteSolrXML(){
		Element root = new Element("delete");
//		<delete><id>05991</id></delete>
//		String ds = "<delete><id>article-" + getId() + "</id></delete>";
		root.addContent(getDeleteField("article-" + getId()));
		Document doc = new Document(root);
		StringWriter sw = new StringWriter();
	    try {
	        XMLOutputter serializer = new XMLOutputter();
	        serializer.output(doc, sw);
	        return sw.toString();
	      }
	      catch (IOException e) {
	        System.err.println(e);
	        return "";
	      }
	}
	
	
	private String getAddSolrXML(){
		Element root = new Element("add");
		Element doce = new Element("doc");
		root.addContent(doce);
		TimeZone dtz = TimeZone.getTimeZone("GMT");
		sdf.setTimeZone(dtz);
		doce.addContent(getOneField("uniqueKey", "article-" + getId()));
		doce.addContent(getOneField("title", getTitle()));
		doce.addContent(getOneField("content", getHtmlStripContent()));
		doce.addContent(getOneField("siteId", getSiteId()+""));
		doce.addContent(getOneField("excerpt", getNotEmptyExcerpt()));
		doce.addContent(getOneField("id", getId() + ""));
		doce.addContent(getOneField("url", getUrl()));
		if(getCreatedAt() != null)
			doce.addContent(getOneField("createdAt", sdf.format(getCreatedAt())));
		if(getPublishedAt() != null)
			doce.addContent(getOneField("publishedAt", sdf.format(getPublishedAt())));
		if(getUpdatedAt() != null)
			doce.addContent(getOneField("updatedAt", sdf.format(getUpdatedAt())));
		
		boolean hasTitleImg = getTitleImg() == null ? false : true;
		boolean hasAttachments = getAttachments().size() > 0 ? true : false;
		boolean hasTags = getTags().size() > 0 ? true :false;
		
		doce.addContent(getOneField("hasTags", String.valueOf(hasTags)));
		
		if(hasTags){
			for(Tag t : getTags()){
				doce.addContent(getOneField("tag", t.getName()));
			}
		}
		
		if(getDisplayAuthor() != null){
			doce.addContent(getOneField("author", getDisplayAuthor()));
		}else{
			if(getCreator() != null){
				doce.addContent(getOneField("author", getCreator().getLoginName()));
			}
		}
		doce.addContent(getOneField("hasTitleImg", String.valueOf(hasTitleImg)));
		if(hasTitleImg){
			doce.addContent(getOneField("titleImg", getTitleImg().getUrl()));
		}
		doce.addContent(getOneField("hasAttachments", String.valueOf(hasAttachments)));
		if(hasAttachments){
			for(Asset a : getAttachments()){
				doce.addContent(getOneField("attachment", a.getUrl()));
			}
		}
		for(Section s:getSections()){
			doce.addContent(getOneField("section", s.getName() + "," + s.getId()));
		}
	
		Document doc = new Document(root);
		StringWriter sw = new StringWriter();
	    try {
	        XMLOutputter serializer = new XMLOutputter();
	        serializer.output(doc, sw);
	        return sw.toString();
	      }
	      catch (IOException e) {
	        System.err.println(e);
	        return "";
	      }
//		  <field name="id">0579B002</field>
//		  <field name="name">Canon PIXMA MP500 All-In-One Photo Printer</field>
//		  <field name="manu">Canon Inc.</field>
//		  <field name="cat">electronics</field>
//		  <field name="cat">multifunction printer</field>
//		  <field name="cat">printer</field>
//		  <field name="cat">scanner</field>
//		  <field name="cat">copier</field>
//		  <field name="features">Multifunction ink-jet color photo printer</field>
//		  <field name="features">Flatbed scanner, optical scan resolution of 1,200 x 2,400 dpi</field>
//		  <field name="features">2.5" color LCD preview screen</field>
//		  <field name="features">Duplex Copying</field>
//		  <field name="features">Printing speed up to 29ppm black, 19ppm color</field>
//		  <field name="features">Hi-Speed USB</field>
//		  <field name="features">memory card: CompactFlash, Micro Drive, SmartMedia, Memory Stick, Memory Stick Pro, SD Card, and MultiMediaCard</field>
//		  <field name="weight">352</field>
//		  <field name="price">179.99</field>
//		  <field name="popularity">6</field>
//		  <field name="inStock">true</field>
//		  <!-- Buffalo store -->
//		  <field name="store">45.17614,-93.87341</field>
	}
	
	public String toSolrXML(String op){
		if("add".equalsIgnoreCase(op)){
			return getAddSolrXML();
		}else if("delete".equalsIgnoreCase(op)){
			return getDeleteSolrXML();
		}else{
			return "";
		}
	}

	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd,String hint,
			ErrorMessages errors) {
		if(isAdd){
			return addRelation(bm, hint, errors);
		}else{// is remove
			return removeRelation(bm, hint, errors);
		}
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public void setSiteId(Integer siteId){
		this.siteId = siteId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setDefaultSectionId(int defaultSectionId) {
		this.defaultSectionId = defaultSectionId;
	}
	
	public int getDefaultSectionId(){
		return defaultSectionId;
	}


	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}
	
	public String getPublishedAt(String format) {
		if(getPublishedAt() == null)return "";
		DateFormat df = new SimpleDateFormat(format);
		return df.format(getPublishedAt());
	}
	
	public String getPublishedAt(String format,String tformat) {
		if(getPublishedAt() == null)return "";
		SpecialDate sd = new SpecialDate();
		DateFormat df;
		if(sd.isDateToday(getPublishedAt())){
			df = new SimpleDateFormat(tformat);
		}else{
			df = new SimpleDateFormat(format);
		}
		return df.format(getPublishedAt());
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public String getTplName() {
		if(tplName == null || tplName.isEmpty())return "article";
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}

	public int getHitNum() {
		return hitNum;
	}

	public void setDisplayAuthor(String displayAuthor) {
		this.displayAuthor = displayAuthor;
	}

	public String getDisplayAuthor() {
		if(displayAuthor == null || displayAuthor.isEmpty()){
			return getCreator().getLoginName();
		}
		return displayAuthor;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	
	public boolean canComment(){
		if(getCommentLevel() == 2)return false;//如果禁止评论，false
		WebSite ws = emp.get().find(WebSite.class, siteId);
		if(ws.isCommentClosed())return false;//如果站点评论关闭，return false；
		if(getCommentLevel() == 0){
			return ws.getCommentLevel() > 0; //如果继承自站点，那么站点必须是大于0
		}else{
			return true;
		}
	}

	public void setCommentLevel(int commentLevel) {
		this.commentLevel = commentLevel;
	}

	public int getCommentLevel() {
		return commentLevel;
	}

	public void setKeyValues(String keyValues) {
		this.keyValues = keyValues;
	}

	public String getKeyValues() {
		return keyValues;
	}

	public void setKeyValueMap(Map<String, String> keyValueMap) {
		this.keyValueMap = keyValueMap;
	}

	public Map<String, String> getKeyValueMap() {
		return keyValueMap;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setProtectLevel(String protectLevel) {
		this.protectLevel = protectLevel;
	}

	public String getProtectLevel() {
		return protectLevel;
	}
}

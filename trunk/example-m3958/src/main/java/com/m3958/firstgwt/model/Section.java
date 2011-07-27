package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.m3958.firstgwt.utils.ModernPaginator;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;
import com.m3958.firstgwt.utils.Paginator;
import com.m3958.firstgwt.utils.SimplePaginator;

@Entity
@Table(name="SECTIONS")
@NamedQueries({
	@NamedQuery(name="findTopSectionByName",
			query="SELECT s FROM Section AS s WHERE s.siteId = :siteId AND s.name = :name AND s.parent IS NULL"),
	@NamedQuery(name="findTopSections",
			query="SELECT s FROM Section s WHERE s.siteId = :siteId AND s.parent IS NULL AND s.hidden = FALSE ORDER BY s.position ASC"),
	@NamedQuery(name="findSectionsByNameAndParent",
			query="SELECT s FROM Section s WHERE s.siteId = :siteId AND s.parent = :parent AND s.name = :name"),
	@NamedQuery(name="findSectionsByIds",
			query="SELECT s FROM Section s WHERE s.id IN :sectionIds")			
			
})
public class Section extends BaseModel<Section> implements TreeModel<Section>,HasCreator,HasObjectPermission,HasRelation,IHasSiteId{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_TOP_BY_NAME = "findTopSectionByName";
		public final static String FIND_TOP = "findTopSections";
		public final static String FIND_BY_NAME_PARENT = "findSectionsByNameAndParent";
		public final static String FIND_BY_IDS = "findSectionsByIds";
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
		jc.setExcludes(JsonExcludeFields.SECTION_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="SECTION_ID")
	protected int id;
	
	private String tplName;
	
	private String name;
	
	private int siteId;
	
	private int perpage;
	
	private int position;
	
	private boolean hidden;
	
	private boolean sectionContainer;
	
	private String protectLevel;
	
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
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Section parent;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="SECTION_ARTICLE")
	@OrderBy("publishedAt DESC")
	private List<Article> articles = new ArrayList<Article>();
	
	@OneToMany(mappedBy="parent")
	@OrderBy("position ASC")
	private List<Section> children = new ArrayList<Section>();
	
	private String parentIds;
	
	public Section getParent() {
		return parent;
	}
	
	/**
	 * 属于该目录的有特定标记的文章
	 * @param flag
	 * @param num
	 * @return 属于该目录的有特定标记的文章
	 */
	public List<Article> getArticlesByFlag(String flag,int num){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.FIND_SECTION_FLAG_ARTICLE);
		q.setParameter("section", this);
		q.setParameter("audit", true);
		q.setParameter("flag", flag);
		q.setMaxResults(num);
		List<Article> ars =  q.getResultList();
		return ars;
	}
	
	/**
	 * 兄弟节点，不包括自己
	 * @return 兄弟节点，不包括自己
	 */
	public List<Section> getSiblings(){
		List<Section> sbs = new ArrayList<Section>();
		if(getParent()!=null){
			for(Section s:getParent().getChildren()){
				if( s != this && !s.getHidden()){
					sbs.add(s);
				}
			}
		}else{
			for(Section s : getSite(siteId).getTopSections()){
				if(s!=this){
					sbs.add(s);
				}
			}
		}
		return sbs;
	}
	
	/**
	 * 兄弟节点，包括自己
	 * @return 兄弟节点，包括自己
	 * 
	 */
	public List<Section> getSiblingsIncludeMe(){
		if(getParent()!=null){
			List<Section> sbs = new ArrayList<Section>();
			for(Section s:getParent().getChildren()){
				if(!s.getHidden()){
					sbs.add(s);
				}
			}
			return sbs;
		}else{
			return getSite(siteId).getTopSections();
		}
	}
	
	/**
	 * 面包屑，从顶层目录到自己的路径
	 * @return List<Section>
	 */
	public List<Section> getBreadCrumb(){
		List<Section> pp = new ArrayList<Section>();
		Section p = this;
		while(p!=null){
			pp.add(p);
			p = p.getParent();
		}
		Collections.reverse(pp);
		return pp;
	}

	public void setParent(Section parent) {
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
	
	
	public boolean hasArticles(){
		return getArticleCount() > 0;
	}
	
	public boolean hasChildrenArticles(){
		return getChildrenArticleCount() > 0;
	}
	
	public boolean hasOneArticle(){
		return getArticleCount() == 1;
	}

	@Override
	public boolean isFolder() {
		return !children.isEmpty();
	}

	@Override
	public boolean addChildren(Section bm) {
		if(!children.contains(bm)){
			children.add(bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(Section bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}
	
	/**
	 * 此目录的url路径
	 * @return 此目录的url路径
	 */
	public String getUrl(){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("section",getTplName(),getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + getTplName()+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=section&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	public String getUrl(String tplName){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("section",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=section&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	/**
	 * 得到一页文章，页的大小是目录属性的一部分。
	 * @param pageNum
	 * @return 一页文章
	 */
	public List<Article> getArticlePage(int pageNum){
		if(isSectionContainer()){
			return getChildrenArticlePage(pageNum);
		}
		if(pageNum < 1)pageNum = 1;
		return getArticles((pageNum-1)*getPerpage(), getPerpage());
	}
	
	public List<Article> getChildrenArticlePage(int pageNum){
		if(pageNum < 1)pageNum = 1;
		return getChildrenArticles((pageNum-1)*getPerpage(), getPerpage());
	}


	/**
	 * 简单分页器
	 * @param currentPage 此参数直接在模板里面提供，输入currentPage即可。
	 * @return 简单分页器
	 */
	public SimplePaginator getSimplePaginator(int currentPage){
		if(isSectionContainer()){
			return new SimplePaginator(getChildrenArticleCount(), getPerpage(), currentPage);
		}else{
			return new SimplePaginator(getArticleCount(), getPerpage(), currentPage);
		}
	}
	
	public Paginator getPaginator(int currentPage,int pageWindow){
		if(isSectionContainer()){
			return new Paginator(getChildrenArticleCount(),getPerpage(), pageWindow, currentPage);
		}else{
			return new Paginator(getArticleCount(),getPerpage(), pageWindow, currentPage);
		}
	}
	
	/**
	 * 现代化的分页器，此分页器的html是固定的，在java代码里面输出。
	 * @param currentPage 直接引用即可。
	 * @param pageWindow 显示的页数的一半，比如1,2,3,4,5,6,7,当前页是4的话，那么窗口的大小就是3.
	 * @param prepostPageNum 省略号前后的页数。比如：1,2...3,4,5,6,7,8,9...100,101,那么这个值就是2
	 * @param prename 前一页的显示：比如 《前一页
	 * @param postname 后一页的显示：比如：后一页》
	 * @param uriAndQs 直接引用即可。
	 * @return 现代分页器
	 */
	public ModernPaginator getModernPaginator(int currentPage,int pageWindow,int prepostPageNum,String prename,String postname,String uriAndQs){
		if(isSectionContainer()){
			return new ModernPaginator(getChildrenArticleCount(), getPerpage(), pageWindow, prepostPageNum, currentPage,prename,postname,uriAndQs);
		}else{
			return new ModernPaginator(getArticleCount(), getPerpage(), pageWindow, prepostPageNum, currentPage,prename,postname,uriAndQs);
		}
		
	}

	
	/**
	 * 目录的文章，从0开始，按发布日期逆向排序。
	 * @param num
	 * @return 目录的文章，从0开始，按发布日期逆向排序。
	 */
	public List<Article> getArticles(int num){
		return getArticles(0, num);
	}
	/**
	 * 目录的文章，从start开始，按发布日期逆向排序。
	 * @param start
	 * @param num
	 * @return 目录的文章，从start开始，按发布日期逆向排序。
	 */
	public List<Article> getArticles(int start,int num){
		List<Article> ars = new ArrayList<Article>();
		Query q = emp.get().createNamedQuery(Article.NamedQueries.GET_SECTION_ARTICLE);
		q.setParameter("section", this);
		q.setParameter("audit", true);
		q.setFirstResult(start);
		q.setMaxResults(num);
		ars =  q.getResultList();
		return ars;
	}
	/**
	 * 子目录的文章，从0开始
	 * @param num
	 * @return 子目录的文章，从0开始
	 */
	public List<Article> getChildrenArticles(int num){
		return getChildrenArticles(0, num);
	}

	/**
	 * 子目录的文章，从start开始
	 * @param start
	 * @param num
	 * @return 子目录的文章，从start开始
	 */
	public List<Article> getChildrenArticles(int start,int num){
		if(isFolder()){
			List<Article> ars = new ArrayList<Article>();
			Query q = emp.get().createNamedQuery(Article.NamedQueries.GET_SECTION_CHILDREN_ARTICLE);
			q.setParameter("sectionId", "%," + this.getId() + ",%");
			q.setParameter("audit", true);
			q.setFirstResult(start);
			q.setMaxResults(num);
			ars =  q.getResultList();
			return ars;
		}else{
			return getArticles(start, num);
		}
	}
	
	public int getChildrenArticleCount(){
		if(isFolder()){
			Query q = emp.get().createNamedQuery(Article.NamedQueries.SECTION_CHILDREN_ARTICLE_COUNT);
			q.setParameter("sectionId", "%," + this.getId() + ",%");
			q.setParameter("audit", true);
			Long c =  (Long) q.getSingleResult();
			return c.intValue();
		}else{
			return getArticleCount();
		}
	}

	
	public int getArticleCount(){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.SECTION_ARTICLE_COUNT);
		q.setParameter("section", this);
		q.setParameter("audit", true);
		Long c =  (Long) q.getSingleResult();
		return c.intValue();
	}
	
	
	public List<Article> getSearchPage(String tkw,String ckw,boolean isAnd ,int pageNum){
		return searchArticles(tkw,ckw,isAnd,(pageNum - 1)*getPerpage(),getPerpage());
	}
	

	public SimplePaginator getSearchPaginator(String tkw,String ckw,boolean isAnd,int currentPage){
		SimplePaginator spt = new SimplePaginator(getSearchCount(tkw,ckw,isAnd), getPerpage(), currentPage);
		return spt;
	}

	
	public List<Article> searchArticles(String tkw,String ckw,boolean isAnd, int num){
		return searchArticles(tkw,ckw,isAnd, 0,num);
	}
	
	public List<Article> searchArticles(String tkw,String ckw,boolean isAnd ,int start,int num){
		Query q;
		boolean hasEmpty = tkw == null || tkw.isEmpty() || ckw == null || ckw.isEmpty();
		if(isFolder()){
			if(isAnd){
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_CHILDREN_ARTICLE_AND);
			}else{
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_CHILDREN_ARTICLE_OR);
			}
			if(hasEmpty)q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_CHILDREN_ARTICLE_AND);
			q.setParameter("sectionId", "%," + this.getId() + ",%");
		}else{
			if(isAnd){
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_ARTICLE_AND);
			}else{
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_ARTICLE_OR);
			}
			q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_ARTICLE_AND);
			q.setParameter("section", this);			
		}
		
		String ttkw = "%" + tkw + "%";
		String cckw = "%" + ckw + "%";
		
		q.setParameter("audit", true);
		q.setParameter("title", ttkw);
		q.setParameter("content", cckw);
		q.setFirstResult(start);
		q.setMaxResults(num);
		List<Article> ars =  q.getResultList();
		return ars;
	}
	
	public int getSearchCount(String tkw,String ckw,boolean isAnd){
		Query q;
		if(isFolder()){
			if(isAnd){
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_CHILDREN_ARTICLE_COUNT_AND);
			}else{
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_CHILDREN_ARTICLE_COUNT_OR);
			}
			q.setParameter("sectionId", "%," + this.getId() + ",%");
		}else{
			if(isAnd){
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_ARTICLE_COUNT_AND);
			}else{
				q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_SECTION_ARTICLE_COUNT_OR);
			}
			q.setParameter("section", this);
		}
		String ttkw = "%" + tkw + "%";
		String cckw = "%" + ckw + "%";
		q.setParameter("audit", true);
		q.setParameter("title", ttkw);
		q.setParameter("content", cckw);
		return ((Long) q.getSingleResult()).intValue();
	}


	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="SECTION_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="SECTION_OBJECTROLE")
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

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Article> getArticles() {
		List<Article> ars = new ArrayList<Article>();
		for(Article a : articles){
			if(a.isAudit())ars.add(a);
		}
		return ars;
	}

	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd, String hint,
			ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof Article){
				if(articles.contains(bm)){
					return false;
				}else{
					articles.add((Article) bm);
					return true;
				}
			}
		}else{
			if(bm instanceof Article){
				if(articles.contains(bm)){
					articles.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getName(int length){
		if(name != null && name.length() > length){
			return name.substring(0,length);
		}else{
			return name;
		}
	}
	
	public String getName(int length,String postFix){
		if(name != null && name.length() > length){
			return name.substring(0,length -1) + postFix;
		}else{
			return name;
		}
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSiteId() {
		return siteId;
	}

	@Override
	public List<Section> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<Section> children) {
		this.children = children;
	}

	public void setKeyValueMap(Map<String, String> keyValueMap) {
		this.keyValueMap = keyValueMap;
	}

	public Map<String, String> getKeyValueMap() {
		return keyValueMap;
	}

	public void setKeyValues(String keyValues) {
		this.keyValues = keyValues;
	}

	public String getKeyValues() {
		return keyValues;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean getHidden() {
		return hidden;
	}

	public String getTplName() {
		if(tplName == null || tplName.isEmpty())return "section";
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public int getPerpage() {
		if(perpage == 0)return 20;
		return perpage;
	}
	
	public boolean getIsRoot(){
		if(getParent() == null)return true;
		return false;
	}

	public void setSectionContainer(boolean sectionContainer) {
		this.sectionContainer = sectionContainer;
	}

	public boolean isSectionContainer() {
		return sectionContainer;
	}

	public void setProtectLevel(String protectLevel) {
		this.protectLevel = protectLevel;
	}

	public String getProtectLevel() {
		return protectLevel;
	}


}

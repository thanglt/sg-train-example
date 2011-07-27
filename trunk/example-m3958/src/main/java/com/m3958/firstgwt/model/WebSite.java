package com.m3958.firstgwt.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.jpa.JpaHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.PageHitObject;
import com.m3958.firstgwt.client.types.VisitStatItem;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.m3958.firstgwt.utils.ObjectPermissionUtil;
import com.m3958.firstgwt.utils.SimplePaginator;
import com.m3958.firstgwt.utils.SpecialDate;

@Entity
@Table(name="WEBSITES",uniqueConstraints={@UniqueConstraint(columnNames={"ename"})})
public class WebSite extends BaseModel<WebSite> implements HasCreator,HasObjectPermission{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static ObjectOperation[] possibleOperations = ObjectOperation.values();
	
	private static ObjectRoleName[] possibleObjectRoles = ObjectRoleName.values();
	
	public ObjectRoleName[] getPossibleRoleNames(){
		return possibleObjectRoles;
	}
	
	
	public ObjectOperation[] getPossibleOperations(){
		return possibleOperations;
	}
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.WEBSITE_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="WEBSITE_ID")
	protected int id;
	
	@Transient
	private boolean isSiteOwner;
	
	@Transient
	private boolean isSiteEditor;
	
	private String cname;
	
	private String ename;
	
	private String logoUrl;
	
	private String styleName;
	
	private boolean cacheEnable;
	
	private String displayAuthors;
	
	private String articleSources;
	
	private int commentLevel;
	
	private boolean commentClosed;
	
	private boolean stop;
	
	private String imgSizes;
	
	private boolean searchFriendUrl;
	
	private String articleFlags;
	
	private String title;
	
	private int perpage;
	
	private String richEditorCssPath;
	
	private String metaKeywords;
	
	private String metaDescription;
	
	private int rssDateNum;
	
	@OneToMany(mappedBy="webSite",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST})
	private List<WebHost> webhosts = new ArrayList<WebHost>();
	

	@OneToMany(mappedBy="webSite",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST})
	private List<PageMistake> mistakes = new ArrayList<PageMistake>();

	
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	private String bz;
	
	
	private String type;
	


	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}


	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getBz() {
		return bz;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
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
	@JoinTable(name="WEBSITE_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="WEBSITE_OBJECTROLE")
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
		return cname;
	}
	
	@Override
	public Permission getPermission(ObjectOperation opCode) {
		return ObjectPermissionUtil.getPermission(this, opCode);
	}
	
	public List<User> getArticleWriter(int num,String orderBy){
		String qs = "SELECT u,COUNT(a) FROM Article AS a,IN(a.creator) AS u WHERE a.siteId = :siteId GROUP BY u ORDER BY COUNT(a)";
		return null;
	}
	

	public List<Article> getSearchPage(String tkw,String ckw,boolean isAnd ,int pageNum){
		if(pageNum < 1)pageNum = 1;
		return searchArticles(tkw,ckw,isAnd,(pageNum - 1)*getPerpage(),getPerpage());
	}
	
	public SimplePaginator getSimplePaginator(int currentPage){
		SimplePaginator spt = new SimplePaginator(getArticleCount(), getPerpage(), currentPage);
		return spt;
	}
	

	public SimplePaginator getSearchPaginator(String tkw,String ckw,boolean isAnd,int currentPage){
		SimplePaginator spt = new SimplePaginator(getSearchCount(tkw,ckw,isAnd), getPerpage(), currentPage);
		return spt;
	}

	
	public List<Article> searchArticles(String tkw,String ckw,boolean isAnd, int num){
		return searchArticles(tkw,ckw,isAnd, 0,num);
	}
	
	public <T> List<List<T>> getListList(List<T> list,int listsize){
		List<List<T>> ll = new ArrayList<List<T>>();
		List<T> l = null;
		for(int i=0;i<list.size();i++){
			if(i % listsize == 0){
				if(l!=null)ll.add(l);
				l = new ArrayList<T>();
				l.add(list.get(i));
			}else{
				l.add(list.get(i));
			}
		}
		if( l!= null && l.size()>0)ll.add(l);
		return ll;
	}
	
	public List<Article> searchArticles(String tkw,String ckw,boolean isAnd ,int start,int num){
		
		Query q;
		if(isAnd){
			q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_ARTICLES_AND);
		}else{
			q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_ARTICLES_OR);
		}
		
		if(tkw == null || tkw.isEmpty() || ckw == null || ckw.isEmpty()){
			q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_ARTICLES_AND);
		}
		
		String ttkw = "%" + tkw + "%";
		String cckw = "%" + ckw + "%";
		q.setParameter("siteId", getId());
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
		if(isAnd){
			q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_COUNT_AND);
		}else{
			q = emp.get().createNamedQuery(Article.NamedQueries.SEARCH_COUNT_OR);
		}
		String ttkw = "%" + tkw + "%";
		String cckw = "%" + ckw + "%";
		q.setParameter("siteId", getId());
		q.setParameter("audit", true);
		q.setParameter("title", ttkw);
		q.setParameter("content", cckw);
		return ((Long) q.getSingleResult()).intValue();
	}
	

	/**
	 * 文章可以自由设定一个标记，比如：首页滚动，首页幻灯等等，可以通过这个标记来找到特定的文章
	 * @param flag 标记
	 * @param num 数量
	 * @return 具有指定标记的指定数量的文章
	 */
	public List<Article> getArticlesByFlag(String flag,int num){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.FIND_FLAG_ARTICLE);
		q.setParameter("siteId", getId());
		q.setParameter("audit", true);
		q.setParameter("flag", flag);
		q.setMaxResults(num);
		List<Article> ars =  q.getResultList();
		return ars;
	}
	
	public List<Article> getArticlesByFlag(String flag,int num,String yuesu){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.FIND_FLAG_ARTICLE);
		q.setParameter("siteId", getId());
		q.setParameter("audit", true);
		q.setParameter("flag", flag);
		q.setMaxResults(num);
		List<Article> ars =  q.getResultList();
		if("titleImg".equalsIgnoreCase(yuesu)){
			List<Article> arss = new ArrayList<Article>();
			for(Article a : ars){
				if(a.getTitleImg() != null){
					arss.add(a);
				}
			}
			return arss;
		}
		return ars;
	}
	
	/**
	 * 提供一系列目录的ID，找到这个目录中的文章，按照发布日期逆向排序。
	 * @param sectionIds List<Integer> 目录ID列表
	 * @param num 文章数量
	 * @return 文章列表
	 */
	public List<Article> getArticleBySectionIds(List<Integer> sectionIds,int num){
		return getArticleBySectionIds(sectionIds, 0, num);
	}

	public Article findArticle(int aid){
		return emp.get().find(Article.class, aid);
	}
	
	public List<Article> getArticleBySectionIds(List<Integer> sectionIds,int start,int num){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.GET_SECTIONS_ARTICLE);
		q.setParameter("sectionIds", sectionIds);
		q.setParameter("audit", true);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}

	
	public List<Article> getArticleBySectionId(int sectionId,int num){
		return getArticleBySectionId(sectionId, 0, num);
	}

	
	public List<Article> getArticleBySectionId(int sectionId,int start,int num){
		Section s = emp.get().find(Section.class, sectionId);
		if(s == null)return new ArrayList<Article>();
		return s.getArticles(start, num);
	}

	
	public int getArticleCount(){
		String qs = "SELECT COUNT(a) FROM Article AS a WHERE a.siteId = :siteId AND a.audit = :audit";
		Query q = emp.get().createQuery(qs);
		q.setParameter("siteId", getId());
		q.setParameter("audit", true);
		Long c =  (Long) q.getSingleResult();
		return c.intValue();
	}
	

	@SuppressWarnings("unchecked")
	public List<Section> getTopSections(){
		Query q = emp.get().createNamedQuery(Section.NamedQueries.FIND_TOP);
		q.setParameter("siteId", getId());
		List<Section> ss =  q.getResultList();
		return ss;
	}
	
	/**
	 * 通过目录的路径查找站点的目录。
	 * @param sectionName 目录路径，用斜杠分隔。比如：/课程/数学
	 * @return 对应的目录
	 * 
	 * @see Section
	 */
	
	public Section findSection(String sectionName){
		try {
			if(sectionName.startsWith("/"))sectionName = sectionName.substring(1);
			String[] sns = sectionName.split("/");
			Query q = emp.get().createNamedQuery(Section.NamedQueries.FIND_TOP_BY_NAME);
			q.setParameter("siteId", getId());
			q.setParameter("name", sns[0]);
			Section s =  (Section) q.getSingleResult();
			
			for(int i=1;i<sns.length;i++){
				q = emp.get().createNamedQuery(Section.NamedQueries.FIND_BY_NAME_PARENT);
				q.setParameter("siteId", getId());
				q.setParameter("parent", s);
				q.setParameter("name", sns[i]);
				s = (Section) q.getSingleResult();
			}
			if(s != null){
				return s;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过ID查找目录
	 * @param sectionId 目录ID
	 * @return 目录
	 * 
	 * @see Section
	 */
	
	public Section findSection(int sectionId){
		try {
			return emp.get().find(Section.class, sectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AssetFolder findAssetFolder(int fid){
		try {
			return emp.get().find(AssetFolder.class, fid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AssetFolder findAssetFolder(String name){
		try {
			if(name.startsWith("/"))name = name.substring(1);
			String[] lcns = name.split("/");
			Query q = emp.get().createNamedQuery(AssetFolder.NamedQueries.FIND_TOP_BY_NAME);
			q.setParameter("siteId", getId());
			q.setParameter("name", lcns[0]);
			AssetFolder af =  (AssetFolder) q.getSingleResult();
			for(int i=1;i<lcns.length;i++){
				q = emp.get().createNamedQuery(AssetFolder.NamedQueries.FIND_BY_NAME_PARENT);
				q.setParameter("siteId", getId());
				q.setParameter("parent", af);
				q.setParameter("name", lcns[i]);
				af = (AssetFolder) q.getSingleResult();
			}
			return af;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AssetFolder> getTopAssetFolders(){
		Query q = emp.get().createNamedQuery(AssetFolder.NamedQueries.FIND_TOP);
		q.setParameter("siteId", getId());
		return  q.getResultList();
	}
	
	/**
	 * 返回站点顶级目录的链接列表
	 * 
	 * @return 返回站点顶级目录的链接列表
	 */
	@SuppressWarnings("unchecked")
	public List<Link> getTopLinks(){
		Query q = emp.get().createNamedQuery(Link.NamedQueries.FIND_TOP);
		q.setParameter("siteId", getId());
		List<Link> ls =  q.getResultList();
		return ls;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Link> getTopLinks(int startPosition){
		Query q = emp.get().createNamedQuery(Link.NamedQueries.FIND_TOP);
		q.setParameter("siteId", getId());
		q.setFirstResult(startPosition);
		List<Link> ls =  q.getResultList();
		return ls;
	}

	
	public Link findLink(String linkName){
		try {
			if(linkName.startsWith("/"))linkName=linkName.substring(1);
			String[] lcns = linkName.split("/");
			Query q = emp.get().createNamedQuery(Link.NamedQueries.FIND_TOP_BY_NAME);
			q.setParameter("siteId", getId());
			q.setParameter("name", lcns[0]);
			Link l =  (Link) q.getSingleResult();
			for(int i=1;i<lcns.length;i++){
				q = emp.get().createNamedQuery(Link.NamedQueries.FIND_BY_NAME_PARENT);
				q.setParameter("siteId", getId());
				q.setParameter("parent", l);
				q.setParameter("name", lcns[i]);
				l = (Link) q.getSingleResult();
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vote findVote(String voteName){
		try {
			if(voteName.startsWith("/"))voteName=voteName.substring(1);
			String[] lcns = voteName.split("/");
			Query q = emp.get().createNamedQuery(Vote.NamedQueries.FIND_TOP_BY_NAME);
			q.setParameter("siteId", getId());
			q.setParameter("name", lcns[0]);
			Vote v =  (Vote) q.getSingleResult();
			for(int i=1;i<lcns.length;i++){
				q = emp.get().createNamedQuery(Vote.NamedQueries.FIND_BY_NAME_PARENT);
				q.setParameter("siteId", getId());
				q.setParameter("parent", v);
				q.setParameter("name", lcns[i]);
				v = (Vote) q.getSingleResult();
			}
			return v;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vote findVote(int voteId){
		try {
			emp.get().find(Vote.class, voteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public XinJianCat findXinjianCat(String name){
		try {
			if(name.startsWith("/"))name = name.substring(1);
			String[] sns = name.split("/");
			Query q = emp.get().createNamedQuery(XinJianCat.NamedQueries.FIND_TOP_BY_NAME);
			q.setParameter("siteId", getId());
			q.setParameter("name", sns[0]);
			XinJianCat xjc =  (XinJianCat) q.getSingleResult();
			
			for(int i=1;i<sns.length;i++){
				q = emp.get().createNamedQuery(XinJianCat.NamedQueries.FIND_BY_NAME_PARENT);
				q.setParameter("siteId", getId());
				q.setParameter("parent", xjc);
				q.setParameter("name", sns[i]);
				xjc = (XinJianCat) q.getSingleResult();
			}
			if(xjc != null){
				return xjc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public XinJianCat findXinjianCat(int id){
		try {
			return emp.get().find(XinJianCat.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<XinJianCat> getTopXinjianCats(){
		Query q = emp.get().createNamedQuery(XinJianCat.NamedQueries.FIND_TOP);
		q.setParameter("siteId", getId());
		List<XinJianCat> xjcs =  q.getResultList();
		return xjcs;
	}
	
	private String getInClause(List<PageHitObject> phos){
		StringBuffer sb = new StringBuffer("(");
		for(PageHitObject pho : phos){
			sb.append(pho.getObId()).append(",");
		}
		if(sb.indexOf(",") != -1){
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			return sb.toString();
		}
		
		return "";
	}
	
	private int getObjectHitNum(List<PageHitObject> phos,int i){
		for(PageHitObject pho : phos){
			if(pho.getObId() == i)return pho.getHitNum();
		}
		return 0;
	}
	
	public Long getVisitNumber(){
		Query q = emp.get().createNamedQuery(PageHit.NamedQueries.FIND_PAGE_HIT_BY_SITE);
		q.setParameter("siteId", getId());
		SpecialDate startDate = new SpecialDate();
		q.setParameter("startDate", startDate.Y1700());
		return (Long) q.getSingleResult();
	}
	
	
	public List<VisitStatItem> getDailyVisitNumber(){
		Query q = emp.get().createNamedQuery(PageHit.NamedQueries.SITE_DAY_VISIT_NUM);
		q.setParameter("siteId", getId());
		SpecialDate startDate = new SpecialDate();
		q.setParameter("startDate", startDate.Y1700());
		return q.getResultList();
	}

	
	public List<Article> getTopVisitArticles(int num){
		Query q = emp.get().createNamedQuery(PageHit.NamedQueries.FIND_TOP_VISITS);
		q.setParameter("siteId", getId());
		q.setParameter("obname", "article");
		q.setMaxResults(num);
		List<PageHitObject> phos = q.getResultList();
		String inClause = getInClause(phos);
		if(inClause.isEmpty()){
			return new ArrayList<Article>();
		}
		q = emp.get().createQuery("SELECT a FROM Article AS a WHERE a.id IN " + inClause);
		List<Article> ars = q.getResultList();
		for(Article a : ars){
			a.setHitNum(getObjectHitNum(phos, a.getId()));
		}
		return ars;
	}
	
	public List<Article> getTopVisitArticles(int start,int num){
		Query q = emp.get().createNamedQuery(PageHit.NamedQueries.FIND_TOP_VISITS);
		q.setParameter("siteId", getId());
		q.setParameter("obname", "article");
		q.setFirstResult(start);
		q.setMaxResults(num);
		List<PageHitObject> phos = q.getResultList();
		String inClause = getInClause(phos);
		if(inClause.isEmpty()){
			return new ArrayList<Article>();
		}
		q = emp.get().createQuery("SELECT a FROM Article AS a WHERE a.id IN " + inClause);
		List<Article> ars = q.getResultList();
		for(Article a : ars){
			a.setHitNum(getObjectHitNum(phos, a.getId()));
		}
		return ars;
	}
	
//	public List<String> getArchiveNames(){
//		Query q = JpaHelper.getReadAllQuery(query)
//	}
	/**
	 * 站点文章按年月分卷
	 * @return List<String[]> 格式如下：[2011,1],[2011,2]
	 */
	public List<String[]> getArticleArchives(){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.ARTICLE_ARCHIVES);
		q.setParameter("siteId", getId());
		q.setParameter("audit", true);
		return q.getResultList();
	}
	
	/**
	 * 指定年份的按月分卷
	 * @param year 年份整数 比如：2011
	 * @return List<String> [1,2,3]
	 */
	public List<String> getYearArticleArchives(int year){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.ARTICLE_ARCHIVES_IN_ONE_YEAR);
		q.setParameter("siteId", getId());
		q.setParameter("year", year);
		q.setParameter("audit", true);
		return q.getResultList();
	}

	/**
	 * 指定年份的按月分卷
	 * @param year 年份字串 比如：2011
	 * @return List<String> [1,2,3]
	 */
	public List<String> getYearArticleArchives(String year){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.ARTICLE_ARCHIVES_IN_ONE_YEAR);
		q.setParameter("siteId", getId());
		q.setParameter("year", year);
		q.setParameter("audit", true);
		return q.getResultList();
	}
	
	
	/**
	 * 月份分卷文章
	 * @param year 年份，整数
	 * @param month 月份，整数
	 * @return 文章
	 */
	
	public List<Article> getArchiveArticles(int year,int month){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.ARCHIVE_ARTICLES);
		q.setParameter("siteId", getId());
		q.setParameter("year", year);
		q.setParameter("month", month);
		q.setParameter("audit", true);
		return q.getResultList();
	}

	/**
	 * 月份分卷文章
	 * @param year 年份，字串
	 * @param month 月份，字串
	 * @return 文章
	 */

	public List<Article> getArchiveArticles(String year,String month){
		Query q = emp.get().createNamedQuery(Article.NamedQueries.ARCHIVE_ARTICLES);
		q.setParameter("siteId", getId());
		q.setParameter("year", year);
		q.setParameter("month", month);
		q.setParameter("audit", true);
		return q.getResultList();
	}


	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}


	public String getStyleName() {
		return styleName;
	}


	public void setWebhosts(List<WebHost> webhosts) {
		this.webhosts = webhosts;
	}


	public List<WebHost> getWebhosts() {
		return webhosts;
	}


	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}


	public String getLogoUrl() {
		return logoUrl;
	}


	public void setCacheEnable(boolean cacheEnable) {
		this.cacheEnable = cacheEnable;
	}


	public boolean isCacheEnable() {
		return cacheEnable;
	}


	public void setStop(boolean stop) {
		this.stop = stop;
	}


	public boolean isStop() {
		return stop;
	}


	public void setSearchFriendUrl(boolean searchFriendUrl) {
		this.searchFriendUrl = searchFriendUrl;
	}


	public boolean isSearchFriendUrl() {
		return searchFriendUrl;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitle() {
		return title;
	}


	public void setImgSizes(String imgSizes) {
		this.imgSizes = imgSizes;
	}


	public String getImgSizes() {
		return imgSizes;
	}


	public void setDisplayAuthors(String displayAuthors) {
		this.displayAuthors = displayAuthors;
	}


	public String getDisplayAuthors() {
		return displayAuthors;
	}


	public void setArticleSources(String articleSources) {
		this.articleSources = articleSources;
	}


	public String getArticleSources() {
		return articleSources;
	}


	public void setArticleFlags(String articleFlags) {
		this.articleFlags = articleFlags;
	}


	public String getArticleFlags() {
		return articleFlags;
	}


	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}


	public int getPerpage() {
		if(perpage == 0)return 20;
		return perpage;
	}


	public void setCommentLevel(int commentLevel) {
		this.commentLevel = commentLevel;
	}


	public int getCommentLevel() {
		return commentLevel;
	}


	public void setCommentClosed(boolean commentClosed) {
		this.commentClosed = commentClosed;
	}


	public boolean isCommentClosed() {
		return commentClosed;
	}


	public void setSiteOwner(boolean isSiteOwner) {
		this.isSiteOwner = isSiteOwner;
	}


	public boolean isSiteOwner() {
		return isSiteOwner;
	}


	public void setSiteEditor(boolean isSiteEditor) {
		this.isSiteEditor = isSiteEditor;
	}


	public boolean isSiteEditor() {
		return isSiteEditor;
	}


	public void setMistakes(List<PageMistake> mistakes) {
		this.mistakes = mistakes;
	}


	public List<PageMistake> getMistakes() {
		return mistakes;
	}


	public File getThemeRoot(String siteRoot, String path) {
		File f = new File(siteRoot, getId()+"");
		f = new File(f, path);
		return f;
	}


	public void setRichEditorCssPath(String richEditorCssPath) {
		this.richEditorCssPath = richEditorCssPath;
	}


	public String getRichEditorCssPath() {
		return richEditorCssPath;
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


	public void setRssDateNum(int rssDateNum) {
		this.rssDateNum = rssDateNum;
	}


	public int getRssDateNum() {
		return rssDateNum;
	}
}

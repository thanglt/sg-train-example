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
import javax.persistence.UniqueConstraint;

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
@Table(name="XJ_CAT",uniqueConstraints = { @UniqueConstraint(columnNames = { "name","siteId" })})
@NamedQueries({
	@NamedQuery(name="findTopXJCatByName",
			query="SELECT x FROM XinJianCat AS x WHERE x.siteId = :siteId AND x.name = :name AND x.parent IS NULL"),
	@NamedQuery(name="findTopXJCats",
			query="SELECT x FROM XinJianCat x WHERE x.siteId = :siteId AND x.parent IS NULL ORDER BY x.position ASC"),
	@NamedQuery(name="findXjCatByNameAndParent",
			query="SELECT x FROM XinJianCat x WHERE x.siteId = :siteId AND x.parent = :parent AND x.name = :name")
})
public class XinJianCat extends BaseModel<XinJianCat>  implements TreeModel<XinJianCat>,HasCreator,HasObjectPermission,HasRelation,IHasSiteId{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_TOP_BY_NAME = "findTopXJCatByName";
		public final static String FIND_TOP = "findTopXJCats";
		public final static String FIND_BY_NAME_PARENT = "findXjCatByNameAndParent";
	}
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.XINJIAN_CAT_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}	
	
	private String tplName;
	
	private int perpage;

	
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
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ADDRESS_ID")
	protected int id;
	
	private String name;
	
	private int siteId;
	
	private int position;
	
	private boolean xjcatContainer;
	
	public int getSiteId() {
		return siteId;
	}


	protected String creatorIds;
	
	@ManyToOne
	@JoinColumn(name="CREATOR_ID",referencedColumnName="USER_ID")
	protected User creator;

	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="XX_CAT_OBJECTPERMISSION")
	private List<Permission> objectPermissions = new ArrayList<Permission>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name="XX_CAT_OBJECTROLE")
	private List<Role> objectRoles = new ArrayList<Role>();


	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="XX_CAT_XX")
	@OrderBy("createdAt DESC")
	private List<XinJian> xinjians = new ArrayList<XinJian>();

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private XinJianCat parent;
	
	
	@OneToMany(mappedBy="parent")
	private List<XinJianCat> children = new ArrayList<XinJianCat>();
	
	
	
	
	public String getUrl(){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("xjcat",getTplName(),getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + getTplName()+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=xjcat&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}
	
	public String getUrl(String tplName){
		if(getSite(siteId).isSearchFriendUrl()){
			return getFriendUrl("xjcat",tplName,getId());
		}else{
			return "/?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName+ "&" + AppConstants.OBNAME_PARAMETER_NAME + "=xjcat&" + AppConstants.OBID_PARAMETER_NAME + "=" + id;
		}
	}

	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
		creatorIds = getCreatorPath(creator);
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public List<XinJianCat> getBreadCrumb(){
		List<XinJianCat> pp = new ArrayList<XinJianCat>();
		XinJianCat p = this;
		while(p!=null){
			pp.add(p);
			p = p.getParent();
		}
		Collections.reverse(pp);
		return pp;
	}
	

	
	public List<XinJianCat> getSiblings(){
		List<XinJianCat> sbs = new ArrayList<XinJianCat>();
		if(getParent()!=null){
			for(XinJianCat s:getParent().getChildren()){
				if( s != this){
					sbs.add(s);
				}
			}
		}else{
			for(XinJianCat s: getSite(siteId).getTopXinjianCats()){
				if(s!=this){
					sbs.add(s);
				}
			}
			
		}
		return sbs;
	}

	public List<XinJianCat> getSiblingsIncludeMe(){
		if(getParent()!=null){
			return getParent().getChildren();
		}else{
			return getSite(siteId).getTopXinjianCats();
		}
	}


	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd, String hint,
			ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof XinJian){
				if(xinjians.contains(bm)){
					return false;
				}else{
					xinjians.add((XinJian)bm);
					return true;
				}
			}
		}else{
			if(bm instanceof XinJian){
				if(xinjians.contains(bm)){
					xinjians.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public List<Permission> getObjectPermissions() {
		return objectPermissions;
	}

	@Override
	public void setObjectPermissions(List<Permission> permissions) {
		this.objectPermissions = permissions;
	}

	@Override
	public void createObjectPermissions() {
		ObjectPermissionUtil.createObjectPermissions(this);
		
	}

	@Override
	public void createObjectRoles() {
		ObjectPermissionUtil.createObjectRoles(this);
	}

	@Override
	public Role createObjectRole(ObjectRoleName orn) {
		return ObjectPermissionUtil.createObjectRole(this, orn);
	}

	@Override
	public List<Role> getObjectRoles() {
		return objectRoles;
	}

	@Override
	public Role getObjectRole(ObjectRoleName orn) {
		return ObjectPermissionUtil.getObjectRole(this, orn);
	}

	@Override
	public Permission getPermission(ObjectOperation opCode) {
		return ObjectPermissionUtil.getPermission(this, opCode);
	}

	@Override
	public String getOname() {
		return name;
	}

	@Override
	public boolean addObjectRole(Role r) {
		return ObjectPermissionUtil.addObjectRole(objectRoles, r);
	}

	@Override
	public boolean addObjectPermission(Permission p) {
		return ObjectPermissionUtil.addObjectPermission(objectPermissions, p);
	}

	public String getCreatorIds() {
		return creatorIds;
	}
	
	public XinJianCat getParent() {
		return parent;
	}

	private String parentIds;
	
	private int persistedParentId = SmartConstants.NONE_EXIST_MODEL_ID;
	
	public void setPersistedParentId(int pid){
		persistedParentId = pid;
	}

	public int getParentId() {
		return persistedParentId;
	}



	public void setParent(XinJianCat parent) {
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


	@Override
	public List<XinJianCat> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<XinJianCat> children) {
		this.children = children;
	}

	@Override
	public boolean addChildren(XinJianCat bm) {
		if(!children.contains(bm)){
			children.add(bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildren(XinJianCat bm) {
		if(children.contains(bm)){
			children.remove(bm);
			return true;
		}
		return false;
	}

	@Override
	public boolean isFolder() {
		return !children.isEmpty();
	}

	public void setXinjians(List<XinJian> xinjians) {
		this.xinjians = xinjians;
	}

	public List<XinJian> getXinjians() {
		return xinjians;
	}
	
	public List<XinJian> getXinjianPage(int pageNum){
		return getXinjians((pageNum-1)*getPerpage(), getPerpage());
	}
	
	public List<XinJian> getXinjians(int num) {
		return getXinjians(0, num);
	}
	
	public SimplePaginator getSimplePaginator(int currentPage){
		SimplePaginator spt = new SimplePaginator(getXinjianCount(), getPerpage(), currentPage);
		return spt;
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
		if(isXjcatContainer()){
			return new ModernPaginator(getChildrenXinjianCount(), getPerpage(), pageWindow, prepostPageNum, currentPage,prename,postname,uriAndQs);
		}else{
			return new ModernPaginator(getXinjianCount(), getPerpage(), pageWindow, prepostPageNum, currentPage,prename,postname,uriAndQs);
		}
		
	}
	
	
	public Paginator getPaginator(int currentPage,int pageWindow){
		Paginator pg = new Paginator(getXinjianCount(),getPerpage(), pageWindow, currentPage);
		return pg;
	}
	
	public boolean hasXinjian(){
		return getXinjianCount()>0;
	}
	
	public boolean hasChildrenXinjian(){
		return getChildrenXinjianCount()>0;
	}
	
	public List<XinJian> getXinjians(int start,int num){
		List<XinJian> ars = new ArrayList<XinJian>();
		Query q = emp.get().createNamedQuery(XinJian.NamedQueries.FIND_XJCAT_XINJIANS);
		q.setParameter("xjCat", this);
		q.setParameter("audit", true);
		q.setParameter("gongkai", true);
		q.setFirstResult(start);
		q.setMaxResults(num);
		ars =  q.getResultList();
		return ars;
	}

	
	public List<XinJian> getChildrenXinjians(int start,int num){
		List<XinJian> ars = new ArrayList<XinJian>();
		Query q = emp.get().createNamedQuery(XinJian.NamedQueries.FIND_XJCAT_CHILDREN_XINJIANS);
		q.setParameter("xjCatId", "%," + this.getId() + ",%");
		q.setParameter("audit", true);
		q.setParameter("gongkai", true);
		q.setFirstResult(start);
		q.setMaxResults(num);
		ars =  q.getResultList();
		return ars;
	}
	
	public int getChildrenXinjianCount(){
		Query q = emp.get().createNamedQuery(XinJian.NamedQueries.XJCAT_CHILDREN_XINJIAN_COUNT);
		q.setParameter("xjCatId", "%," + this.getId() + ",%");
		q.setParameter("audit", true);
		q.setParameter("gongkai", true);
		Long c =  (Long) q.getSingleResult();
		return c.intValue();
	}

	
	public int getXinjianCount(){
		Query q = emp.get().createNamedQuery(XinJian.NamedQueries.XJCAT_XINJIAN_COUNT);
		q.setParameter("xjCat", this);
		q.setParameter("audit", true);
		q.setParameter("gongkai", true);
		Long c =  (Long) q.getSingleResult();
		return c.intValue();
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public String getTplName() {
		if(tplName == null || tplName.isEmpty())return "xjcat";
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

	public void setXjcatContainer(boolean xjcatContainer) {
		this.xjcatContainer = xjcatContainer;
	}

	public boolean isXjcatContainer() {
		return xjcatContainer;
	}
}

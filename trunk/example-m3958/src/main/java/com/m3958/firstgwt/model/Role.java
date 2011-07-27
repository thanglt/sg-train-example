package com.m3958.firstgwt.model;

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
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;


@Entity
@Table(name = "ROLES")
@NamedQueries({
	@NamedQuery(name="findUserRoles",
			query="SELECT r FROM User AS u,IN(u.roles) r WHERE u.id = ?1"),
	@NamedQuery(name="findMyCreatedRoles",
			query = "SELECT r FROM Role r WHERE r.creatorIds LIKE ?1"),
	@NamedQuery(name="findSupermanRole",
			query = "SELECT r FROM Role r WHERE r.cname = ?1")
//	@NamedQuery(name="findSiteOwnerRole",
//			query = "SELECT r FROM Role r WHERE r.cname = ?1"),
			
})
public class Role extends BaseModel<Role> implements HasCreator,HasRelation{
	
	public static class RoleNamedQueries{
		public final static String FIND_USER_ROLES = "findUserRoles";
		public final static String FIND_SUPERMAN_ROLE = "findSupermanRole";
		public final static String FIND_MY_CREATED_ROLES = "findMyCreatedRoles";
	}

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.ROLE_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ROLE_ID")
	protected int id;
	

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2972606766741655373L;
	
	private String orname;
	private String cname;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST})
	@JoinTable(name="ROLE_PERMISSION",joinColumns=
		@JoinColumn(name="R_ID",referencedColumnName="ROLE_ID"),
		inverseJoinColumns=@JoinColumn(name="P_ID",referencedColumnName="PERMISSION_ID"))
	private List<Permission> permissions;
	
	public String getOrname() {
		return orname;
	}
	public void setOrname(String orname) {
		this.orname = orname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}

	public boolean addPermission(Permission p){
		if(!permissions.contains(p)){
			permissions.add(p);
			return true;
		}
		return false;
	}
	
	public boolean dropPermission(Permission p){
		if(permissions.contains(p)){
			permissions.remove(p);
			return true;
		}
		return false;
	}

	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd,String hint,ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof Permission){
				if(permissions.contains(bm)){
					return false;
				}else{
					permissions.add((Permission) bm);
					return true;
				}
			}else{
				;
			}
		}else{
			if(bm instanceof Permission){
				if(permissions.contains(bm)){
					permissions.remove(bm);
					return true;
				}else{
					return false;
				}
			}else{
				;
			}
		}
		return false;
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



	
}

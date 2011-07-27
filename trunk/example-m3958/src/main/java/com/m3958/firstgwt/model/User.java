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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;


/**
 * 
 * @author jianglibo@gmail.com
 * 新建用户必须有的权限是：Hmessage，Department，MenuItem，
 * 
 * alter table users add constraint FK_USERS_MLID FOREIGN KEY (MLID) references menulevels(MENULEVEL_ID);
 * ALTER TABLE t1 ENGINE=InnoDB;
 */

@Entity
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }),@UniqueConstraint(columnNames = {"loginName"}),@UniqueConstraint(columnNames = {"fcId"}) })
@NamedQueries({
	@NamedQuery(
			name = "findMyCreatedUsers",
			query="SELECT u FROM User u WHERE  u.creatorIds LIKE ?1"
			)
})
public class User extends BaseModel<User> implements HasRelation,HasCreator{
	
	public static class UserNamedQuerys{
		public static String FIND_MY_CREATED_USERS = "findMyCreatedUsers";
	}

	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.USER_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="USER_ID")
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
	private static final long serialVersionUID = -8812597083444251003L;
	private String thumbnailUrl;
	private boolean fcUser;
	
	private String fcId;
	
	private String loginName;
	
	private String password;
	
	@Transient
	private String confirmPassword;
	@Transient
	private String oldPassword;
	
	private String openid;
	private String passwordHint;
	private String firstName;
	private String lastName;
	@Column(nullable = false, unique = true)
	private String email; 
	private String phoneNumber;
	private String address;
	private String mobile;
	@Temporal(TemporalType.DATE)
	private Date birthDay;
	
	private String nickname;
	
	private String avatar;
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="USERS_ROLES")
	private List<Role> roles = new ArrayList<Role>();
	
	@ManyToMany(mappedBy="users",fetch=FetchType.LAZY)
	private List<Group> groups = new ArrayList<Group>();
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MLID",referencedColumnName="MENULEVEL_ID")
	private MenuLevel menuLevel;
	
	public int getMenuLevelId(){
		if(menuLevel != null){
			return menuLevel.getId();
		}
		return SmartConstants.NONE_EXIST_MODEL_ID;
	}
	

	public boolean addRole(Role r){
		if(r==null)return false;
		if(!roles.contains(r)){
			roles.add(r);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean dropRole(Role r){
		if(roles.contains(r)){
			roles.remove(r);
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean addGroup(Group g){
		if(g==null)return false;
		if(!groups.contains(g)){
			groups.add(g);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean dropGroup(Group g){
		if(groups.contains(g)){
			groups.remove(g);
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public static User findByLoginNameOrEmailOrMobile(String identity) {
		User user = null;
		try {
			if(identity.indexOf('@') != -1){
				user = findByEmail(identity);
			}else if(identity.matches("\\d+")){
				user = findByMobile(identity);
			}else{
				user = findByLoginName(identity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private static User findByLoginName(String identity) {
		return (User) emp.get().createQuery("SELECT u FROM User u WHERE u.loginName = :loginName").
			setParameter("loginName", identity).getSingleResult();
	}

	private static User findByMobile(String identity) {
		return (User) emp.get().createQuery("SELECT u FROM User u WHERE u.mobile = :mobile").
		setParameter("mobile", identity).getSingleResult();
	}

	private static User findByEmail(String identity) {
		return (User) emp.get().createQuery("SELECT u FROM User u WHERE u.email = :email").
		setParameter("email", identity).getSingleResult();
	}
	
	
	public List<HmessageReceiver> getMyHmrsByStatus(HmessageStatus status,int start,int num){
		Query q = emp.get().createNamedQuery(HmessageReceiver.NamedQueries.FIND_MY_HMESSAGE_BY_STATUS);
		q.setParameter("receiver", this);
		q.setParameter("status", status);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}
	
	
	public List<HmessageReceiver> getMyHmrsByStatuses(List<HmessageStatus> statuses,int start,int num){
		Query q = emp.get().createNamedQuery(HmessageReceiver.NamedQueries.FIND_MY_HMESSAGE_BY_STATUSES);
		q.setParameter("receiver", this);
		q.setParameter("statuses", statuses);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}
	
	
	public List<HmessageReceiver> getMyHmrs(int start,int num){
		Query q = emp.get().createNamedQuery(HmessageReceiver.NamedQueries.FIND_MY_HMESSAGE);
		q.setParameter("receiver", this);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}
	
	
	public List<Hmessage> getMyHms(int start,int num){
		Query q = emp.get().createNamedQuery(Hmessage.NamedQueries.FIND_MY_HMESSAGE);
		q.setParameter("sender", this);
		q.setFirstResult(start);
		q.setMaxResults(num);
		return q.getResultList();
	}
	
	
	public List<Hmessage> getMyHmsByStatus(HmessageStatus status,int start,int num){
		if(status == null){
			Query q = emp.get().createNamedQuery(Hmessage.NamedQueries.FIND_MY_HMESSAGE_WITH_NULL_STATUS);
			q.setParameter("sender", this);
			q.setFirstResult(start);
			q.setMaxResults(num);
			return q.getResultList();
		}else{
			Query q = emp.get().createNamedQuery(Hmessage.NamedQueries.FIND_MY_HMESSAGE_BY_STATUS);
			q.setParameter("sender", this);
			q.setParameter("status", status);
			q.setFirstResult(start);
			q.setMaxResults(num);
			return q.getResultList();
		}
	}




	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the confirmPassword
	 */
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @return the email
	 */
	
	public String getEmail() {
		return email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the passwordHint
	 */
	public String getPasswordHint() {
		return passwordHint;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}


	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param passwordHint
	 *            the passwordHint to set
	 */
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getBirthDay() {
		return birthDay;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	


	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	public boolean hasRole(Role r){
		if(roles.contains(r)){
			return true;
		}
		return false;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setFcId(String fcId) {
		this.fcId = fcId;
	}

	public String getFcId() {
		return fcId;
	}

	public void setFcUser(boolean fcUser) {
		this.fcUser = fcUser;
	}

	public boolean getFcUser() {
		return fcUser;
	}

	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd,String hint,ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof Role){
				if(roles.contains(bm)){
					return false;
				}else{
					roles.add((Role) bm);
					return true;
				}
			}else if(bm instanceof Group){
				if(groups.contains(bm)){
					return false;
				}else{
					groups.add((Group) bm);
					return true;
				}
			}
		}else{// is remove
			if(bm instanceof Role){			
				if(roles.contains(bm)){
					roles.remove(bm);
					return true;
				}else{
					return false;
				}
			}else if(bm instanceof Group){			
				if(groups.contains(bm)){
					groups.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Group> getGroups() {
		return groups;
	}

	protected String creatorIds;
	
	@ManyToOne
	@JoinColumn(name="CREATOR_ID",referencedColumnName="USER_ID")
	protected User creator;
	
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		if(creator == null)return;
		this.creator = creator;
		creatorIds = getCreatorPath(creator);
	}

	public String getCreatorIds() {
		return creatorIds;
	}

	public void setMenuLevel(MenuLevel menuLevel) {
		this.menuLevel = menuLevel;
	}

	public MenuLevel getMenuLevel() {
		return menuLevel;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		if(nickname == null || nickname.isEmpty()){
			return getLoginName();
		}
		return nickname;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

}

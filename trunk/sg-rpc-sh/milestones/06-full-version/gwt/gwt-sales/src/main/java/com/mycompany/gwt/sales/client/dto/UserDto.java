package com.mycompany.gwt.sales.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: jingling
 * Date: 8/4/11
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDto implements IsSerializable {

    private String id;// ID
	private Date createDate;// 创建日期
	private Date modifyDate;// 修改日期

    private String username;// 用户名
	private String password;// 密码
    private String email;// E-mail
	private String name;// 姓名
	private String projId;  //项目
    private String department;// 部门
    private Boolean isAccountEnabled;// 账号是否启用
	private Boolean isAccountLocked;// 账号是否锁定
	private Boolean isAccountExpired;// 账号是否过期
	private Boolean isCredentialsExpired;// 凭证是否过期
	private Integer loginFailureCount;// 连续登录失败的次数
    private Date lockedDate;// 账号锁定日期
	private Date loginDate;// 最后登录日期
    private String loginIp;// 最后登录IP
    private Set<RoleDto> roleSet;// 角色集合

    public UserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getAccountEnabled() {
        return isAccountEnabled;
    }

    public void setAccountEnabled(Boolean accountEnabled) {
        isAccountEnabled = accountEnabled;
    }

    public Boolean getAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    public Boolean getAccountExpired() {
        return isAccountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        isAccountExpired = accountExpired;
    }

    public Boolean getCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }

    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Set<RoleDto> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<RoleDto> roleSet) {
        this.roleSet = roleSet;
    }
}

package com.mycompany.gwt.sales.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: jingling
 * Date: 8/4/11
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoleDto implements IsSerializable {

    private String id;// ID
    private Date createDate;// 创建日期
    private Date modifyDate;// 修改日期

    private String name;// 角色名称
    private String value;// 角色标识
    private Boolean isSystem;// 是否为系统内置角色
    private String description;// 描述
    private String ouId;   //组织
    private String projectId;//项目

    private Set<ResourceDto> resourceSet;// 资源

    public RoleDto() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getSystem() {
        return isSystem;
    }

    public void setSystem(Boolean system) {
        isSystem = system;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOuId() {
        return ouId;
    }

    public void setOuId(String ouId) {
        this.ouId = ouId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Set<ResourceDto> getResourceSet() {
        return resourceSet;
    }

    public void setResourceSet(Set<ResourceDto> resourceSet) {
        this.resourceSet = resourceSet;
    }
}

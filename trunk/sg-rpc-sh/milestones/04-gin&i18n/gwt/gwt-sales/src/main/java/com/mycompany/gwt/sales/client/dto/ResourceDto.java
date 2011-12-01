package com.mycompany.gwt.sales.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jingling
 * Date: 8/4/11
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceDto implements IsSerializable {

    private String id;// ID
    private Date createDate;// 创建日期
    private Date modifyDate;// 修改日期

    private String name;// 资源名称
	private String value;// 资源标识
	private Boolean isSystem;// 是否为系统内置资源
	private String description;// 描述
	private Integer seq;// 排序

    public ResourceDto() {
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}

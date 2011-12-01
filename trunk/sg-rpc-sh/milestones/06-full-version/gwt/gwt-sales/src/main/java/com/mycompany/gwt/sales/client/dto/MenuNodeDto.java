package com.mycompany.gwt.sales.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

public class MenuNodeDto implements IsSerializable {

    private String Id; //主键
    private Date createDate;// 创建日期
    private Date modifyDate;// 修改日期

    private String name; //姓名
    private String parentId; //父Id
    private String category;//分类
    private boolean isFolder;//是否属于 folder
    private String icon;//自定义图标
    private String viewId;//用户匹配 view
    private Integer seq;//排序

    public MenuNodeDto() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}


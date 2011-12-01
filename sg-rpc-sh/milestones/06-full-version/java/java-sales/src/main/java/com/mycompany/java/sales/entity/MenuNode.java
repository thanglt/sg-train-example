package com.mycompany.java.sales.entity;

import com.mycompany.java.common.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_sales_menu_node")
public class MenuNode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name; //姓名
    private String parentId; //父Id
    private String category;//分类
    private Integer isFolder;//是否 folder
    private String icon;//自定义图标
    private String viewId;//用户匹配 view
    private Integer seq;//排序

    public MenuNode() {
    }

    public MenuNode(String name, String parentId, String category, Integer isFolder, String icon) {
        this.name = name;
        this.parentId = parentId;
        this.category = category;
        this.isFolder = isFolder;
        this.icon = icon;
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

    @Column(name = "isFolder" , length = 1)
    public Integer getFolder() {
        return isFolder;
    }

    public void setFolder(Integer folder) {
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

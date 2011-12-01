package mytest.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.Column;

public class MenuNodeDto implements IsSerializable {

    private String Id; //主键
    private String name; //姓名
    private String parentId; //父Id
    private String category;//分类
    private boolean isFolder;//是否属于 folder
    private String icon;//自定义图标

    public MenuNodeDto() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
}


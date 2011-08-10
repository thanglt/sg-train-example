package mytest.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_rpc_menu_node")
public class MenuNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Id; //主键
    private String name; //姓名
    private String parentId; //父Id
    private String category;//分类
    private Integer isFolder;//是否属于 folder

    public MenuNode() {
    }

    public MenuNode(String name, String parentId , String category , Integer isFolder) {
        this.name = name;
        this.parentId = parentId;
        this.category = category;
        this.isFolder = isFolder;
    }

    @Id
    @Column(length = 36, nullable = true)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")  //生成器名称，uuid生成类
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

    @Column(name = "isFolder" , length = 1)
    public Integer getFolder() {
        return isFolder;
    }

    public void setFolder(Integer folder) {
        isFolder = folder;
    }
}

package mytest.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_rpc_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Id; //主键
    private String name; //姓名
    private String location; //地址

    // Constructors
    public User() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
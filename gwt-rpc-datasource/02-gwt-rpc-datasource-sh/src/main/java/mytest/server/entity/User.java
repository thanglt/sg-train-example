package mytest.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tbl_comm_user")
public class User {

    private static final long serialVersionUID = 1L;

    private String Id;
    private String username;// 用户名
    private String password;// 密码
    private String email;// E-mail
    private String name;// 姓名

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

    @Column(updatable = false, nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
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

}
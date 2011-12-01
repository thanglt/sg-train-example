package com.mycompany.java.sales.entity;

import com.mycompany.java.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_sales_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;

    public User() {
        super();
    }

    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 5)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

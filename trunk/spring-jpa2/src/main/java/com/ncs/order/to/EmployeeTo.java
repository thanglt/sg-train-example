package com.ncs.order.to;

import javax.persistence.*;
import java.util.Date;


@Entity
@NamedQueries(
        {
//                @NamedQuery(name="getByName" , query="select e from EmployeeTo e where e.name = :name")
        }
)
@Table(name = "tbl_ord_employee")
public class EmployeeTo extends BaseEntity{

    private static final long serialVersionUID = 6958058186018438872L;

//    private String employeeId;
    private String name; //姓名
    private String code; //编号
    private String sex;  //性别
    private int age;    //年龄
    private String phone; //电话
    private String cell; //手机
    private String recipe; //工艺
    private Date birthday;  //生日
    private String remarks; //备注

    public EmployeeTo() {
    }

    @Column(length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

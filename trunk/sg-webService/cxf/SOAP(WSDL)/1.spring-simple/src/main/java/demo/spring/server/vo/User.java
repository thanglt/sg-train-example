package demo.spring.server.vo;

import sun.security.krb5.internal.PAEncTSEnc;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-12-15
 * Time: 上午9:48
 * Description: web service 复杂对象
 */
//propOrder is define the represent field order
@XmlType(propOrder = {"name","birthday","height","married","pet"})
public class User {

    private String name;
    private Date birthday;
    private Integer height;
    private boolean isMarried;
    private Pet pet;

    public User() {
    }

    public User(String name, Date birthday, Integer height, boolean married) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        isMarried = married;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

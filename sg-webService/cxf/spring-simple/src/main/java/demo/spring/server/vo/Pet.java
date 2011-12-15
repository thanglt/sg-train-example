package demo.spring.server.vo;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-12-15
 * Time: 上午10:17
 * Description:
 */
@XmlType(propOrder = {"petName","petAge"})
public class Pet {
    private String petName;
    private Integer petAge;

    public Pet() {
    }

    public Pet(String petName, Integer petAge) {
        this.petName = petName;
        this.petAge = petAge;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getPetAge() {
        return petAge;
    }

    public void setPetAge(Integer petAge) {
        this.petAge = petAge;
    }
}


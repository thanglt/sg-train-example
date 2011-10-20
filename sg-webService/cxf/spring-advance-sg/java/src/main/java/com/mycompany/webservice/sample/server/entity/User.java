package com.mycompany.webservice.sample.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "cxf_user")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;// ID
    private String name;//name
    private String email;//email

    @Id
	@Column(length = 36, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")  //生成器名称，uuid生成类
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

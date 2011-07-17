package com.mycompany.salesDomain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tbl_sales_user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userID;
	private String name;
	
	public User() {
		super();
	}

	@Id
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

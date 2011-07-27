package com.m3958.firstgwt.session;

import java.io.Serializable;
import java.util.Date;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class RemoveConfirm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7696003499169140973L;
	
	private Date createdAt;
	
	private String id;

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}

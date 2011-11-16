package com.spring.datasource;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Eggsy - eggsy_at_eggsylife.co.uk
 *
 */
@XmlRootElement(name = "student")
public class Student {
	private int id = -1;
	private String name = null;
 
	public Student() {
 
	}
 
	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}
 
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
package com.m3958.firstgwt.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name="EXAM")
public class Exam extends BaseModel<Exam> implements HasCreator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="EXAM_ID")
	protected int id;
	
	private int manfen;
	
	private User creator;
	
	@OneToMany(mappedBy="exam",fetch=FetchType.LAZY)
	private List<ExamItem> items;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}

	public void setItems(List<ExamItem> items) {
		this.items = items;
	}

	public List<ExamItem> getItems() {
		return items;
	}

	public void setManfen(int manfen) {
		this.manfen = manfen;
	}

	public int getManfen() {
		return manfen;
	}
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public String getCreatorIds() {
		return null;
	}
}

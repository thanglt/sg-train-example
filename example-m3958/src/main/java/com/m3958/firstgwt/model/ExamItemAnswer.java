package com.m3958.firstgwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name="EXAM_ITEM_ANSWER")
public class ExamItemAnswer extends BaseModel<ExamItemAnswer> implements HasCreator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="EXAM_ITEM_ANSER_ID")
	protected int id;
	
	@ManyToOne
	private ExamItem examitem;
	
	private User creator;

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

	public void setExamitem(ExamItem examitem) {
		this.examitem = examitem;
	}

	public ExamItem getExamitem() {
		return examitem;
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

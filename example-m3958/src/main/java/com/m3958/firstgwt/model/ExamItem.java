package com.m3958.firstgwt.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name="EXAM_ITEM")
public class ExamItem extends BaseModel<ExamItem>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="EXAM_ITEM_ID")
	protected int id;
	
	@ManyToOne
	private Exam exam;
	
	private int fenshu;
	
	@OneToMany(mappedBy="examitem",fetch=FetchType.LAZY)
	private List<ExamItemAnswer> answers;

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

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Exam getExam() {
		return exam;
	}

	public void setAnswers(List<ExamItemAnswer> answers) {
		this.answers = answers;
	}

	public List<ExamItemAnswer> getAnswers() {
		return answers;
	}

	public void setFenshu(int fenshu) {
		this.fenshu = fenshu;
	}

	public int getFenshu() {
		return fenshu;
	}
}

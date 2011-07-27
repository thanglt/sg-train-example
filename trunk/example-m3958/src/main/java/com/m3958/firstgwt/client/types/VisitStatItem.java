package com.m3958.firstgwt.client.types;

import java.util.Date;

public class VisitStatItem {
	
	private Date date;
	private Long visitNum;
	
	public VisitStatItem(Date date,Long visitNum){
		this.date = date;
		this.visitNum = visitNum;
	}
	
	public VisitStatItem(Date date,int visitNum){
		this.date = date;
		this.visitNum = Long.valueOf(visitNum);
	}
	

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(Long visitNum) {
		this.visitNum = visitNum;
	}
	
}

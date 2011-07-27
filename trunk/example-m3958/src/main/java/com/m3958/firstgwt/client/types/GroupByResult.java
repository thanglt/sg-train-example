package com.m3958.firstgwt.client.types;

import java.util.Date;

public class GroupByResult {
	
	private Long count;
	private String groupByName;
	private Date groupByDate;
	
	public GroupByResult(String groupByName,Long count){
		this.count = count;
		this.groupByName = groupByName;
	}
	
	public GroupByResult(Date groupByDate,Long count){
		this.count = count;
		this.setGroupByDate(groupByDate);
	}

	public void setGroupByName(String groupByName) {
		this.groupByName = groupByName;
	}
	public String getGroupByName() {
		return groupByName;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getCount() {
		return count;
	}

	public void setGroupByDate(Date groupByDate) {
		this.groupByDate = groupByDate;
	}

	public Date getGroupByDate() {
		return groupByDate;
	}
	
}

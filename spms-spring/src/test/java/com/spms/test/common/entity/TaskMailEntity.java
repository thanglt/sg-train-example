package com.spms.test.common.entity;

import java.util.Date;
import java.util.List;



public class TaskMailEntity {

	private MesysUser user;
	
	public void setUserInfo(MesysUser user){
		this.user=user;
	}
	
	public MesysUser getUserInfo(){
		return user;
	}
	
	private String taskName;
	private int costTime;
	private float completePre;
	
	private List<StepInfo> stepList;
	
	private String admin;
	
	private String taskUrl;
	
	private Date time;
	
	public static class StepInfo{
		private String step;
		private String status;
				
		public void setStep(String step) {
			this.step = step;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getStep() {
			return step;
		}
		public String getStatus() {
			return status;
		}		
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

	public float getCompletePre() {
		return completePre;
	}

	public void setCompletePre(float completePre) {
		this.completePre = completePre;
	}

	public List<StepInfo> getStepList() {
		return stepList;
	}

	public void setStepList(List<StepInfo> stepList) {
		this.stepList = stepList;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public Boolean getIsMore(){
		if(completePre>50){
			return true; 
		}else{
			return null;
		}
	}
	
}

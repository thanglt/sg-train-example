package com.skynet.spms.opertrack;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.skynet.common.gwt.GwtActionHelper;
import com.skynet.spms.persistence.entity.base.baseEntity.BaseEntity;
import com.skynet.spms.persistence.entity.base.logEntity.GeneralOperateLogEntity;
import com.skynet.spms.persistence.entity.spmsdd.GeneralOperateLogStatus;

public class TrackInfo {
	
	
	private GeneralOperateLogEntity logEntity=new GeneralOperateLogEntity();

	public static TrackInfo getUpdateTrack(BaseEntity entity,Serializable id){
		return new TrackInfo(entity,id,GeneralOperateLogStatus.modify);
	}
	
	public static TrackInfo getInsertTrack(BaseEntity entity,Serializable id){
		return new TrackInfo(entity,id,GeneralOperateLogStatus.create);
	}
	private TrackInfo(BaseEntity entity, Serializable id,GeneralOperateLogStatus type) {
		
		logEntity.setRecordId((String) id);
		logEntity.setActionDate(new Date());
		logEntity.setOperator(getCurrUser());
		logEntity.setM_GeneralOperateLogStatus(type);
		logEntity.setRecordName(entity.getClass().getSimpleName());
	}	
	
	private String getCurrUser() {
		String user=GwtActionHelper.getCurrUser();
		if(StringUtils.equals(user, "anonymously")){
			user="System";
		}
		return user;
	}

	private Map<String,String> valMap=new HashMap<String,String>();
	
	
	public void addUpdateStateInfo(String field,Object prevStatus,Object currStatus){
		
		ValueInfo info=new ValueInfo(prevStatus,currStatus);
		valMap.put(field,info.toString());
	}
	
	public void addInsertStateInfo(String field,Object currStatus){
		addUpdateStateInfo(field,null,currStatus);
	}

	
	public GeneralOperateLogEntity getStatusEntity(){
		
		StringBuilder detail=new StringBuilder();
		for(Map.Entry<String, String> entry:valMap.entrySet()){
			detail.append("field=>").append(entry.getKey());
			detail.append(" val=>").append(entry.getValue());	
			detail.append("\n\r");
		}
		logEntity.setActionDescription(detail.toString());

		return logEntity;
	}

	public void setIsDel() {
		logEntity.setM_GeneralOperateLogStatus(GeneralOperateLogStatus.remove);
	}

	public void setVersion(Integer newVer) {
		logEntity.setVersion(newVer);
	}	
	
}

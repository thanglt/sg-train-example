package com.skynet.spms.opertrack;

import java.util.List;
import java.util.Map;

import com.skynet.spms.persistence.entity.base.logEntity.GeneralOperateLogEntity;



public interface TrackManager {

	void insertTrackRecord(List<GeneralOperateLogEntity> info);

	List<GeneralOperateLogEntity> getList(int startRow, int endRow);

	List<GeneralOperateLogEntity> queryList(int startRow, int endRow,
			Map<String, Object> values);
	

}
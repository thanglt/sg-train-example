package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Hmessage;

public class HmessageDao  extends BaseDao<Hmessage>{
	
	@Inject
	public HmessageDao(HmessageChangeStrategy task) {
		super(Hmessage.class);
		extraStrategyTask = task;
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public List<Hmessage> smartNamedQueryFetch() {
		if(Hmessage.NamedQueries.FIND_MY_HMESSAGE.equals(getReqPs().getStringValue(SmartParameterName.NAMED_QUERY_NAME))){
			return getLoginUser().getMyHms(getReqPs().getStartRow(), getReqPs().getEndRow()- getReqPs().getStartRow());
		}else if(Hmessage.NamedQueries.FIND_MY_HMESSAGE_BY_STATUS.equals(getReqPs().getStringValue(SmartParameterName.NAMED_QUERY_NAME))){
			HmessageStatus st = null;
			try {
				st = (HmessageStatus) getReqPs().getEnumValue(HmessageStatus.class, HmessageReceiverField.STATUS.getEname());
			} catch (Exception e) {}
			return getLoginUser().getMyHmsByStatus(st, getReqPs().getStartRow(), getReqPs().getEndRow()- getReqPs().getStartRow());
		}
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}


}

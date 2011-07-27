package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.HmessageReceiver;

public class HmessageReceiverDao  extends BaseDao<HmessageReceiver>{
	
	@Inject
	public HmessageReceiverDao(HmessageReceiverChangeStrategy task) {
		super(HmessageReceiver.class);
		this.extraStrategyTask = task;
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
	public List<HmessageReceiver> smartNamedQueryFetch() {
		if(HmessageReceiver.NamedQueries.FIND_MY_HMESSAGE.equals(getReqPs().getStringValue(SmartParameterName.NAMED_QUERY_NAME))){
			return getLoginUser().getMyHmrs(getReqPs().getStartRow(), getReqPs().getEndRow()- getReqPs().getStartRow());
		}else if(HmessageReceiver.NamedQueries.FIND_MY_HMESSAGE_BY_STATUS.equals(getReqPs().getStringValue(SmartParameterName.NAMED_QUERY_NAME))){
			HmessageStatus st = HmessageStatus.UNREAD;
			try {
				st = (HmessageStatus) getReqPs().getEnumValue(HmessageStatus.class, HmessageReceiverField.STATUS.getEname());
			} catch (Exception e) {}
			return getLoginUser().getMyHmrsByStatus(st, getReqPs().getStartRow(), getReqPs().getEndRow()- getReqPs().getStartRow());
		}else if(HmessageReceiver.NamedQueries.FIND_MY_HMESSAGE_BY_STATUSES.equals(getReqPs().getStringValue(SmartParameterName.NAMED_QUERY_NAME))){
			String[] sts = getReqPs().getMvParaValues(HmessageReceiverField.STATUS.getEname());
			if(sts == null || sts.length == 0){
				return getLoginUser().getMyHmrsByStatuses(Lists.newArrayList(HmessageStatus.UNREAD,HmessageStatus.READED),getReqPs().getStartRow(), getReqPs().getEndRow()- getReqPs().getStartRow());
			}
		}
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}



}

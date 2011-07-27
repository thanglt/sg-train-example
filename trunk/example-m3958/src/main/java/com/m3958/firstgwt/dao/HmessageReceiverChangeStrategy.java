package com.m3958.firstgwt.dao;

import java.util.Date;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.model.Hmessage;
import com.m3958.firstgwt.model.HmessageReceiver;

public class HmessageReceiverChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<HmessageReceiver> {
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	


	@Override
	public boolean extraPersistTask(HmessageReceiver model){
		return true;
	}




	@Override
	public boolean extraRemoveTask(HmessageReceiver model) {
		Hmessage hm = model.gethMessage();
		if(hm.getStatus() == HmessageStatus.DELETED && hm.getHmrCount() < 1){
			HmessageDao hmdao = injector.getInstance(HmessageDao.class);
			hmdao.remove(hm);
		}
		return true;
	}

	@Override
	public boolean extraUpdateTask(HmessageReceiver model,HmessageReceiver newModel){
		if(model.getStatus() == HmessageStatus.UNREAD && newModel.getStatus() == HmessageStatus.READED){
			newModel.setReadTime(new Date());
		}
		return true;
	}


	@Override
	public boolean afterPersist(HmessageReceiver model) {
		return true;
	}
	

}

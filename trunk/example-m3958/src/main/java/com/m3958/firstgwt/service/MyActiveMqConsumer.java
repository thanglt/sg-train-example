package com.m3958.firstgwt.service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


import com.m3958.firstgwt.model.AssetVo;

public class MyActiveMqConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			if (message != null) {
				if (message instanceof ObjectMessage) {
					Object o = ((ObjectMessage) message).getObject();
					if(o instanceof AssetVo){
						processAsset((AssetVo) o);
					}
				}
			}
		} catch (Exception e) {}
	}

	private void processAsset(AssetVo ao) {
		System.out.println(ao.getId());
		
	}
}

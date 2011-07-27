package com.m3958.firstgwt.service;

import org.apache.activemq.broker.BrokerService;

import com.google.inject.Singleton;


@Singleton
public class MyMqBrokerService {
	
	private BrokerService broker;
	
	
	public MyMqBrokerService(){
//	      try {
//			BrokerService broker = new BrokerService();
//			  broker.setUseJmx(true);
//			  broker.addConnector("tcp://localhost:61616");
//			  broker.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void setBroker(BrokerService broker) {
		this.broker = broker;
	}

	public BrokerService getBroker() {
		return broker;
	}
	
	
	
}

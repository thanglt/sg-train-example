package com.m3958.firstgwt.service;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;


@Singleton
public class MyActiveMqConsumerService{
	
	@Inject
	private Injector injector;
	

    private Timer t;
    
	public MyActiveMqConsumerService(){
		startTimmer();
	}
	
	private Thread getTh(){
		return new ConsumerThread(injector.getInstance(MyActiveMqConsumer.class));
//		return new ConsumerThread(new MyActiveMqConsumer());
	}
	
	private void startTimmer(){
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				getTh().start();
			}
		}, 20000, 20000);
	}
	
}

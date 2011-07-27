package com.m3958.firstgwt.client.layout;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.types.ViewNameEnum;

@Singleton
public class LeftStrip extends AppToolStrip{
	
	@Inject
	public LeftStrip(MyEventBus eventBus) {
		super(eventBus, ViewNameEnum.LEFT_STRIP);
	}

}

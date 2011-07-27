package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.types.ViewNameEnum;

@Singleton
public class Strip extends AppToolStrip{
	
	@Inject
	public Strip(MyEventBus eventBus) {
		super(eventBus, ViewNameEnum.STRIP);
	}

}

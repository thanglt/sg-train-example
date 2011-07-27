package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.types.ViewNameEnum;


@Singleton
public class RightStrip extends AppToolStrip{
	
	@Inject
	public RightStrip(MyEventBus eventBus) {
		super(eventBus, ViewNameEnum.RIGHT_STRIP);
	}

}

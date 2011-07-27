package com.m3958.firstgwt.client.layout;

import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class WelcomeLayout extends VLayout implements Iview{
	public WelcomeLayout(){
		setWidth100();
		setHeight100();
		Img img = new Img("/images/logo/110.jpg");
		addMember(img);
	}

	@Override
	public void changeDisplay(ViewAction va, String... paras) {
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.WELCOME;
	}

	@Override
	public Layout asLayout() {
		return this;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public void setUnInitialized() {
	}
}

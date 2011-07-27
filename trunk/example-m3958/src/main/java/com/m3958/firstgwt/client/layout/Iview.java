package com.m3958.firstgwt.client.layout;

import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.Layout;

public interface Iview {
	void changeDisplay(ViewAction va,String...paras);
	void hide();
	void show();
	ViewNameEnum getViewName();
	Layout asLayout();
	ViewNameEnum nextViewName(Btname btname);
	Btname[] getStripStatus();
	String[] getParas();
	void setUnInitialized();
}

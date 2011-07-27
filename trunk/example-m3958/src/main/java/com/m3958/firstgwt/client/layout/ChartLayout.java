package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.view.MyChartWidget;

@Singleton
public class ChartLayout extends MyVlayout implements Iview{
	
	@Inject
	private MyChartWidget mw;
	
	public ChartLayout(){
		setMembersMargin(5);
		setWidth100();
		setHeight100();
	}


	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			addMember(mw);
			initialized = true;
		}
	}

	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum getViewName() {
		return null;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}


	@Override
	public void setUnInitialized() {
		
	}

}

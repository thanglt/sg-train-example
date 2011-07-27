package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class NextViewEvent extends GwtEvent<NextViewEventHandler> {
	
	public static Type<NextViewEventHandler> TYPE = new Type<NextViewEventHandler>();
	
	private ViewNameEnum vname;
	
	private ViewNameEnum nextViewName;
	
	private String[] paras;
	
	
	public NextViewEvent(ViewNameEnum vname,ViewNameEnum nextViewName,String...paras){
		this.vname = vname;
		this.nextViewName = nextViewName;
		this.paras = paras;
	}
  
	public ViewNameEnum getVname() {
		return vname;
	}



	public void setVname(ViewNameEnum vname) {
		this.vname = vname;
	}


	@Override
	public Type<NextViewEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NextViewEventHandler handler) {
		handler.onNextViewEvent(this);
	}


	public void setNextViewName(ViewNameEnum nextViewName) {
		this.nextViewName = nextViewName;
	}

	public ViewNameEnum getNextViewName() {
		return nextViewName;
	}

	public void setParas(String[] paras) {
		this.paras = paras;
	}

	public String[] getParas() {
		return paras;
	}
}

package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class RecordEvent extends GwtEvent<RecordEventHandler> {
	
	public static enum RecordEventType{
		SELECT,CLICK,DOUBLE_CLICK,CLICK_SELECT,GRID_CLICK
	}
	
	public static Type<RecordEventHandler> TYPE = new Type<RecordEventHandler>();
	private ViewNameEnum vname;
	
	private String[] paras;
	
	private RecordEventType rtype;
	
	public RecordEvent(ViewNameEnum vname,RecordEventType rtype,String...paras){
		this.vname = vname;
		this.rtype = rtype;
		this.paras = paras;
	}
  
	public ViewNameEnum getVname() {
		return vname;
	}



	public void setVname(ViewNameEnum vname) {
		this.vname = vname;
	}



	public String[] getParas() {
		return paras;
	}



	public void setParams(String[] paras) {
		this.paras = paras;
	}



	@Override
	public Type<RecordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RecordEventHandler handler) {
		handler.onViewEvent(this);
	}

	public void setRtype(RecordEventType rtype) {
		this.rtype = rtype;
	}

	public RecordEventType getRtype() {
		return rtype;
	}

}

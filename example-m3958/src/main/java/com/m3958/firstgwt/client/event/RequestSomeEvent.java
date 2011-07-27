package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.types.ViewNameEnum;


public class RequestSomeEvent extends GwtEvent<RequestSomeEventHandler> {
	
	public static enum AskForWhat{
		FOLDER_ASSET,SECTION_ARTICLE,XJCAT_XJ
	}
	
	public static Type<RequestSomeEventHandler> TYPE = new Type<RequestSomeEventHandler>();
	
	private ViewNameEnum requestViewName;
	
	private AskForWhat askFor;
	
	private String confirmHint;
	
	public RequestSomeEvent(ViewNameEnum myViewName,AskForWhat askFor,String confirmHint){
		this.setRequestViewName(myViewName);
		this.setAskFor(askFor);
		this.setConfirmHint(confirmHint);
	}

	@Override
	public Type<RequestSomeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestSomeEventHandler handler) {
		handler.onRequestSome(this);
	}

	public void setAskFor(AskForWhat askFor) {
		this.askFor = askFor;
	}

	public AskForWhat getAskFor() {
		return askFor;
	}

	public void setRequestViewName(ViewNameEnum requestViewName) {
		this.requestViewName = requestViewName;
	}

	public ViewNameEnum getRequestViewName() {
		return requestViewName;
	}

	public void setConfirmHint(String confirmHint) {
		this.confirmHint = confirmHint;
	}

	public String getConfirmHint() {
		return confirmHint;
	}
}

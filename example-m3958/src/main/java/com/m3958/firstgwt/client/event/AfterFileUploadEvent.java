package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.jso.UploadResponseJso;

public class AfterFileUploadEvent extends GwtEvent<AfterFileUploadEventHandler> {
	public static Type<AfterFileUploadEventHandler> TYPE = new Type<AfterFileUploadEventHandler>();
	
	
	private UploadResponseJso uploadResponse;
	
	
	
	public AfterFileUploadEvent(UploadResponseJso upjso){
		this.setUploadResponse(upjso);
	}


	@Override
	protected void dispatch(AfterFileUploadEventHandler handler) {
		handler.onAfterUpload(this);
		
	}

	@Override
	public Type<AfterFileUploadEventHandler> getAssociatedType() {
		return TYPE;
	}


	public void setUploadResponse(UploadResponseJso uploadResponse) {
		this.uploadResponse = uploadResponse;
	}


	public UploadResponseJso getUploadResponse() {
		return uploadResponse;
	}
}

package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SiteChangeEvent extends GwtEvent<SiteChangeEventHandler> {
	public static Type<SiteChangeEventHandler> TYPE = new Type<SiteChangeEventHandler>();
  
	private String siteId;
	
	private String oldSiteId;
	
	public SiteChangeEvent(String siteId,String oldSiteId){
		this.siteId = siteId;
		this.oldSiteId = oldSiteId;
	}
	
	@Override
	public Type<SiteChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SiteChangeEventHandler handler) {
		handler.onSiteChange(this);
	}


	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setOldSiteId(String oldSiteId) {
		this.oldSiteId = oldSiteId;
	}

	public String getOldSiteId() {
		return oldSiteId;
	}


}

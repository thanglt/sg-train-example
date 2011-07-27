package com.m3958.firstgwt.client.jso;


public class FeedbackJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected FeedbackJso() { }
	  
	  public final native String getFeedbackType()/*-{return this.feedbackType}-*/;
	  public final native String getfeedbackStatus()/*-{return this.feedbackStatus}-*/;
	  public final native String getContent()/*-{return this.content}-*/;
	  public final native String getReplyContent()/*-{return this.replayContent}-*/;
}

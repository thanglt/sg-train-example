package com.m3958.firstgwt.client.types;

import java.util.List;

import com.m3958.firstgwt.client.event.AttachmentBoxEvent.AttachmentBoxEventType;
import com.m3958.firstgwt.client.jso.AssetJso;

public interface IHasRichArea {
	void insertContent(List<AssetJso> cas,AttachmentBoxEventType abe,String hint);
}

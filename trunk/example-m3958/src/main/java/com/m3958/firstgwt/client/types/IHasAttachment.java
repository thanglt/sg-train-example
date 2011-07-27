package com.m3958.firstgwt.client.types;

import com.m3958.firstgwt.client.event.LoginEventHandler;
import com.m3958.firstgwt.client.jso.AssetJso;

public interface IHasAttachment {
	void addAttachment(AssetJso assetJso,UploadFor uploadFor);
}

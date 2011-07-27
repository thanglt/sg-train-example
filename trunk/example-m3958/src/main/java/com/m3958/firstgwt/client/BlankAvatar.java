package com.m3958.firstgwt.client;


import com.smartgwt.client.widgets.Img;

public class BlankAvatar extends Img{
	
	private String isrc = "icons/32/NoPictureDark32.jpg"; 
	
	public BlankAvatar(){
		setSrc(isrc);
		setWidth(32);
		setHeight(32);
	}
	
	public void setToBlank(){
		setSrc(isrc);
	}
}

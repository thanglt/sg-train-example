package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.BlankAvatar;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

@Singleton
public class LgbZpWindow extends Window{
	
	private Img zpImgDetail = new Img();
	
	private Img zpImg;
	
	@Inject
	public LgbZpWindow(final @Named("blankAvatar") Img zpImg){
		this.zpImg = zpImg;
		setTitle("照片显示");
		setWidth(350);
		setHeight(260);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		setAlign(Alignment.CENTER);
		addItem(zpImgDetail);
		zpImgDetail.setImageType(ImageStyle.CENTER);
		zpImg.setImageType(ImageStyle.STRETCH);
		zpImg.setCursor(Cursor.HAND);
		zpImg.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				zpImgDetail.setSrc(zpImg.getSrc());
				show();
			}});
		
		zpImgDetail.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}});
	}
	
	public BlankAvatar getzpImg(){
		return (BlankAvatar) zpImg;
	}
}

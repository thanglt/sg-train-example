package com.m3958.firstgwt.client.gin;

import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;


public class LogoImgProvider implements Provider<Image>{
	
	private final FirstgwtResources resources;
	
	@Inject
	public LogoImgProvider(FirstgwtResources resources){
		this.resources = resources;
	}

	@Override
	public Image get() {
		return new Image(resources.firstgwtLogo());
	}

}

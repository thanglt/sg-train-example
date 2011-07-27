package com.m3958.firstgwt.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

public class Aconfig {
	
	@Test
	public void testMe() throws ConfigurationException{
		Configuration config = new PropertiesConfiguration("usergui.properties");
//		Configuration config = new XMLConfiguration("gui.xml");
//		String backColor = config.getString("colors.background");
//		Dimension size = new Dimension(config.getInt("window.width"),
//		  config.getInt("window.height"));
		String jianglibo = config.getString("com.m3958.firstgwt.model.Lgb");
		System.out.print(jianglibo);

	}

}

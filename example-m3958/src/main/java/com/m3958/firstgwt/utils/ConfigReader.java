package com.m3958.firstgwt.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ConfigReader {
	private XMLConfiguration config;
	
	public XMLConfiguration getConfig() {
		return config;
	}

	public void createConfig(String configFile) throws ConfigurationException{
		config = new XMLConfiguration(configFile);
	}

	public int multiply(int x, int y) {
		return x / y;
	}
}

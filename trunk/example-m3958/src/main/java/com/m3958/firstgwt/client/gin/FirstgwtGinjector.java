package com.m3958.firstgwt.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.m3958.firstgwt.client.App;

@GinModules(FirstgwtGinModule.class)
public interface FirstgwtGinjector extends Ginjector{
	App getApp();
}

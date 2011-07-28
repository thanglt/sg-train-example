package com.train.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;


@GinModules(MyGinModule.class)
public interface MyGinjector extends Ginjector{

    HomePage getHomePage();

}

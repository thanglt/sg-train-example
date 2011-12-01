package com.mycompany.gwt.sales.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.mycompany.gwt.sales.client.view.HomePage;


@GinModules(SalesGinModule.class)
public interface SalesInjector extends Ginjector {

    HomePage getHomePage();

}

package com.mycompany.gwt.sales.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.mycompany.gwt.sales.client.gin.SalesInjector;
import com.mycompany.gwt.sales.client.view.HomePage;

public class Sales implements EntryPoint {

    public void onModuleLoad() {
        final SalesInjector injector = GWT.create(SalesInjector.class);

        HomePage homePage = injector.getHomePage();
        homePage.draw();
    }
}

package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class MyGwt implements EntryPoint {

    public void onModuleLoad() {

        final MyGinjector injector = GWT.create(MyGinjector.class);

        HomePage homePage = injector.getHomePage();
        homePage.draw();

    }
}

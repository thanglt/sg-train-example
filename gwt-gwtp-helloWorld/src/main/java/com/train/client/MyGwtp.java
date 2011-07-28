package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import com.train.client.gin.MyGinjector;

public class MyGwtp implements EntryPoint {

    public void onModuleLoad() {
        MyGinjector ginjector = GWT.create(MyGinjector.class);

        DelayedBindRegistry.bind(ginjector);

        ginjector.getPlaceManager().revealCurrentPlace();
    }
}

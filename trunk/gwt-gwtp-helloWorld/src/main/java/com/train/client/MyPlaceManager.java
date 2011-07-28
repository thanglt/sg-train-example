package com.train.client;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class MyPlaceManager extends PlaceManagerImpl {

    @Inject
    public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
        super(eventBus, tokenFormatter);
    }

    public void revealDefaultPlace() {
        // Using false as a second parameter ensures that the URL in the browser bar
        // is not updated, so the user is not locked in the application.
        revealPlace(new PlaceRequest("main"), false);
    }
}

package com.train.client;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.train.client.presenter.MainPagePresenter;

public class MyPlaceManager extends PlaceManagerImpl {

  @Inject
  public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
    super(eventBus, tokenFormatter);
  }

  public void revealDefaultPlace() {
    revealPlace(new PlaceRequest(MainPagePresenter.nameToken));
  }

}

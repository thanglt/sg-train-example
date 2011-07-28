package com.train.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.train.client.presenter.MainPagePresenter;


@GinModules(MyGinModule.class)
public interface MyGinjector extends Ginjector {

    PlaceManager getPlaceManager();

    EventBus getEventBus();

    AsyncProvider<MainPagePresenter> getMainPagePresenter();


}

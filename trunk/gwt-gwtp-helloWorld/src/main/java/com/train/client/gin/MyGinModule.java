package com.train.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.train.client.MyPlaceManager;
import com.train.client.presenter.MainPagePresenter;
import com.train.client.view.MainPageView;

public class MyGinModule extends AbstractPresenterModule {

    protected void configure() {
        install(new DefaultModule(MyPlaceManager.class));
//        Installing DefaultModule saves you from having to perform all the following bindings:
//        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
//        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
//        bind(RootPresenter.class).asEagerSingleton();
//        bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
//        bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);

        // Presenters
        bindPresenter(MainPagePresenter.class,
                MainPagePresenter.MyView.class,
                MainPageView.class,
                MainPagePresenter.MyProxy.class);

    }
}

/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.samples.tab.client.gin;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.samples.tab.client.*;
import com.gwtplatform.samples.tab.client.presenter.*;
import com.gwtplatform.samples.tab.client.view.*;

/**
 * @author Christian Goudreau
 */
public class ClientModule extends AbstractPresenterModule {
  @Override
  protected void configure() {
    // Default implementation of standard resources
    install(new DefaultModule(GwtptabsamplePlaceManager.class));

    bind(MyConstants.class).in(Singleton.class);
    bind(CurrentUser.class).in(Singleton.class);
    bind(IsAdminGatekeeper.class).in(Singleton.class);

    // Constants
    bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.homeNewsPage);

    // Presenters
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
        MainPageView.class, MainPagePresenter.MyProxy.class);
    bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
        HomeView.class, HomePresenter.MyProxy.class);
    bindPresenter(DialogSamplePresenter.class, DialogSamplePresenter.MyView.class,
        DialogSampleView.class, DialogSamplePresenter.MyProxy.class);
    bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class,
        SettingsView.class, SettingsPresenter.MyProxy.class);
    bindPresenter(AdminAreaPresenter.class, AdminAreaPresenter.MyView.class,
        AdminAreaView.class, AdminAreaPresenter.MyProxy.class);
    bindPresenter(HomeNewsPresenter.class, HomeNewsPresenter.MyView.class,
        HomeNewsView.class, HomeNewsPresenter.MyProxy.class);
    bindPresenter(HomeInfoPresenter.class, HomeInfoPresenter.MyView.class,
        HomeInfoView.class, HomeInfoPresenter.MyProxy.class);
    bindSingletonPresenterWidget(InfoPopupPresenterWidget.class,
        InfoPopupPresenterWidget.MyView.class, InfoPopupView.class);
    bindSingletonPresenterWidget(LocalDialogPresenterWidget.class,
        LocalDialogPresenterWidget.MyView.class, LocalDialogView.class);
    bindSingletonPresenterWidget(GlobalDialogPresenterWidget.class,
        GlobalDialogPresenterWidget.MyView.class, GlobalDialogView.class);
  }
}
package com.train.client.proxy;

import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.train.client.presenter.MainPagePresenter;

@ProxyCodeSplit
@NameToken("main")
public interface MyProxy extends ProxyPlace<MainPagePresenter> {
}

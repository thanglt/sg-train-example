package com.train.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.*;
import com.train.client.service.HelloServiceAsync;
//import com.gwtplatform.samples.basic.shared.SendTextToServer;
//import com.gwtplatform.samples.basic.shared.SendTextToServerResult;

public class ResponsePresenter extends
        Presenter<ResponsePresenter.MyView, ResponsePresenter.MyProxy> {



  @ProxyCodeSplit
  @NameToken(nameToken)
  public interface MyProxy extends Proxy<ResponsePresenter>, Place {
  }

  public interface MyView extends View {
    Button getCloseButton();

    void setServerResponse(String serverResponse);

    void setTextToServer(String textToServer);
  }

  public static final String nameToken = "response";

  public static final String textToServerParam = "textToServer";

  private final HelloServiceAsync helloService;
  private final PlaceManager placeManager;

  private String textToServer;

  @Inject
  public ResponsePresenter(EventBus eventBus, MyView view, MyProxy proxy,
      PlaceManager placeManager, HelloServiceAsync helloService) {
    super(eventBus, view, proxy);
    this.placeManager = placeManager;
    this.helloService = helloService;
  }

  @Override
  public void prepareFromRequest(PlaceRequest request) {
    super.prepareFromRequest(request);
    textToServer = request.getParameter(textToServerParam, null);
  }

  @Override
  protected void onBind() {
    super.onBind();
    registerHandler(getView().getCloseButton().addClickHandler(
        new ClickHandler() {
          public void onClick(ClickEvent event) {
            placeManager.revealPlace(new PlaceRequest(
                MainPagePresenter.nameToken));
          }
        }));
  }

  @Override
  protected void onReset() {
    super.onReset();
    getView().setTextToServer(textToServer);
    getView().setServerResponse("Waiting for response...");
      helloService.sayHello(textToServer , new AsyncCallback<String>() {
          public void onFailure(Throwable throwable) {
              getView().setServerResponse(throwable.getMessage());
          }
          public void onSuccess(String s) {
              getView().setServerResponse(s);
          }
      });
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
}

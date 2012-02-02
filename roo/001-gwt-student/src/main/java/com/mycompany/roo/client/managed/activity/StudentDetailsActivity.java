package com.mycompany.roo.client.managed.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.mycompany.roo.client.managed.request.ApplicationRequestFactory;
import com.mycompany.roo.client.proxy.StudentProxy;
import com.mycompany.roo.client.scaffold.activity.IsScaffoldMobileActivity;
import com.mycompany.roo.client.scaffold.place.ProxyDetailsView;
import com.mycompany.roo.client.scaffold.place.ProxyListPlace;
import com.mycompany.roo.client.scaffold.place.ProxyPlace;
import com.mycompany.roo.client.scaffold.place.ProxyPlace.Operation;
import java.util.Set;

public class StudentDetailsActivity extends StudentDetailsActivity_Roo_Gwt {

    public StudentDetailsActivity(EntityProxyId<com.mycompany.roo.client.proxy.StudentProxy> proxyId, ApplicationRequestFactory requests, PlaceController placeController, ProxyDetailsView<com.mycompany.roo.client.proxy.StudentProxy> view) {
        this.placeController = placeController;
        this.proxyId = proxyId;
        this.requests = requests;
        view.setDelegate(this);
        this.view = view;
    }

    public void editClicked() {
        placeController.goTo(getEditButtonPlace());
    }

    public Place getBackButtonPlace() {
        return new ProxyListPlace(StudentProxy.class);
    }

    public String getBackButtonText() {
        return "Back";
    }

    public Place getEditButtonPlace() {
        return new ProxyPlace(view.getValue().stableId(), Operation.EDIT);
    }

    public String getTitleText() {
        return "View Student";
    }

    public boolean hasEditButton() {
        return true;
    }

    public void onCancel() {
        onStop();
    }

    public void onStop() {
        display = null;
    }

    public void start(AcceptsOneWidget displayIn, EventBus eventBus) {
        this.display = displayIn;
        Receiver<EntityProxy> callback = new Receiver<EntityProxy>() {

            public void onSuccess(EntityProxy proxy) {
                if (proxy == null) {
                    placeController.goTo(getBackButtonPlace());
                    return;
                }
                if (display == null) {
                    return;
                }
                view.setValue((StudentProxy) proxy);
                display.setWidget(view);
            }
        };
        find(callback);
    }
}

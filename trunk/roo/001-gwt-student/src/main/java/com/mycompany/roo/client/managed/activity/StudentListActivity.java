package com.mycompany.roo.client.managed.activity;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.view.client.Range;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.mycompany.roo.client.managed.request.ApplicationRequestFactory;
import com.mycompany.roo.client.proxy.StudentProxy;
import com.mycompany.roo.client.scaffold.ScaffoldMobileApp;
import com.mycompany.roo.client.scaffold.activity.IsScaffoldMobileActivity;
import com.mycompany.roo.client.scaffold.place.AbstractProxyListActivity;
import com.mycompany.roo.client.scaffold.place.ProxyListView;
import java.util.List;

public class StudentListActivity extends StudentListActivity_Roo_Gwt {

    public StudentListActivity(ApplicationRequestFactory requests, ProxyListView<com.mycompany.roo.client.proxy.StudentProxy> view, PlaceController placeController) {
        super(placeController, view, StudentProxy.class);
        this.requests = requests;
    }

    public Place getBackButtonPlace() {
        return ScaffoldMobileApp.ROOT_PLACE;
    }

    public String getBackButtonText() {
        return "Entities";
    }

    public Place getEditButtonPlace() {
        return null;
    }

    public String getTitleText() {
        return "Students";
    }

    public boolean hasEditButton() {
        return false;
    }

    protected Request<java.util.List<com.mycompany.roo.client.proxy.StudentProxy>> createRangeRequest(Range range) {
        return requests.studentRequest().findStudentEntries(range.getStart(), range.getLength());
    }
}

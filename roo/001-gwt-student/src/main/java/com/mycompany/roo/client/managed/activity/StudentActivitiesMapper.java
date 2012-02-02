package com.mycompany.roo.client.managed.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.mycompany.roo.client.managed.request.ApplicationRequestFactory;
import com.mycompany.roo.client.managed.ui.StudentDetailsView;
import com.mycompany.roo.client.managed.ui.StudentEditView;
import com.mycompany.roo.client.managed.ui.StudentListView;
import com.mycompany.roo.client.managed.ui.StudentMobileDetailsView;
import com.mycompany.roo.client.managed.ui.StudentMobileEditView;
import com.mycompany.roo.client.proxy.StudentProxy;
import com.mycompany.roo.client.request.StudentRequest;
import com.mycompany.roo.client.scaffold.ScaffoldApp;
import com.mycompany.roo.client.scaffold.place.CreateAndEditProxy;
import com.mycompany.roo.client.scaffold.place.FindAndEditProxy;
import com.mycompany.roo.client.scaffold.place.ProxyPlace;

public class StudentActivitiesMapper extends StudentActivitiesMapper_Roo_Gwt {

    public StudentActivitiesMapper(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }

    public Activity getActivity(ProxyPlace place) {
        switch(place.getOperation()) {
            case DETAILS:
                return new StudentDetailsActivity((EntityProxyId<StudentProxy>) place.getProxyId(), requests, placeController, ScaffoldApp.isMobile() ? StudentMobileDetailsView.instance() : StudentDetailsView.instance());
            case EDIT:
                return makeEditActivity(place);
            case CREATE:
                return makeCreateActivity();
        }
        throw new IllegalArgumentException("Unknown operation " + place.getOperation());
    }
}

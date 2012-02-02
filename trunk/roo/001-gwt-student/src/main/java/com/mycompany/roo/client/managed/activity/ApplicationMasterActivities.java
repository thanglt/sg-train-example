package com.mycompany.roo.client.managed.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.mycompany.roo.client.managed.request.ApplicationEntityTypesProcessor;
import com.mycompany.roo.client.managed.request.ApplicationRequestFactory;
import com.mycompany.roo.client.managed.ui.StudentListView;
import com.mycompany.roo.client.managed.ui.StudentMobileListView;
import com.mycompany.roo.client.proxy.StudentProxy;
import com.mycompany.roo.client.scaffold.ScaffoldApp;
import com.mycompany.roo.client.scaffold.place.ProxyListPlace;

public final class ApplicationMasterActivities extends ApplicationMasterActivities_Roo_Gwt {

    @Inject
    public ApplicationMasterActivities(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }
}

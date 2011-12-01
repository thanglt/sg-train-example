package com.mycompany.gwt.sales.client.view.main;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mycompany.gwt.sales.client.view.main.MainLayout;
import com.mycompany.gwt.sales.client.view.main.TopLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class HomePage extends VLayout{

    @Inject
    public HomePage(TopLayout topLayout , MainLayout mainLayout) {

//        setBackgroundColor("black");
        setWidth100();
        setHeight100();

        addMember(topLayout);
        addMember(mainLayout);
    }
}

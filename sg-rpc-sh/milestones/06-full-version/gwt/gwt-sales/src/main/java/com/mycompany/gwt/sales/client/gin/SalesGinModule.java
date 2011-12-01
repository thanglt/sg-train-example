package com.mycompany.gwt.sales.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mycompany.gwt.sales.client.view.main.HomePage;
import com.mycompany.gwt.sales.client.view.order.AnotherOrderView;
import com.mycompany.gwt.sales.client.view.order.OrderManageView;
import com.mycompany.gwt.sales.client.view.systemManage.assignRole2User.AssignRole2User;
import com.smartgwt.client.widgets.layout.Layout;

public class SalesGinModule extends AbstractGinModule {

    protected void configure() {

        //view
        bind(Layout.class).annotatedWith(Names.named("homePage")).to(HomePage.class);
//        bind(Layout.class).annotatedWith(Names.named("orderManageView")).to(OrderManageView.class);
//        bind(Layout.class).annotatedWith(Names.named("homePage")).to(OrderManageView.class);
//        bind(Layout.class).annotatedWith(Names.named("homePage")).to(AnotherOrderView.class);

//        bind(Layout.class).annotatedWith(Names.named(ViewNameEnum.orderViewOne.toString())).to(OrderManageView.class).in(Singleton.class);
//        bind(Layout.class).annotatedWith(Names.named(ViewNameEnum.orderViewTwo.toString())).
//                to(AnotherOrderView.class).in(Singleton.class);
//        bind(Layout.class).annotatedWith(Names.named("homePage")).to(AssignRole2User.class);

        //event
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    }
}

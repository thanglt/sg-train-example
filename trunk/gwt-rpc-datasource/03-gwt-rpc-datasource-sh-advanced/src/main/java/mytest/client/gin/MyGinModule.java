package mytest.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.name.Names;
import com.smartgwt.client.widgets.layout.Layout;
import mytest.client.view.MainTabSet;
import mytest.client.view.sample2.HomePage2;

public class MyGinModule extends AbstractGinModule {

    protected void configure() {

        //inject...
//        bind(VLayout.class).annotatedWith(Names.named("homePage")).to(HomePage.class);
//        bind(Layout.class).annotatedWith(Names.named("homePage")).to(HomePage2.class);
        bind(Layout.class).annotatedWith(Names.named("homePage")).to(MainTabSet.class);
    }
}

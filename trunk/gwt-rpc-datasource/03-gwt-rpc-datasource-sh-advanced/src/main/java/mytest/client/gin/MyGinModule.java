package mytest.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.name.Names;
import com.smartgwt.client.widgets.layout.VLayout;
import mytest.client.view.HomePage;

public class MyGinModule extends AbstractGinModule {

    protected void configure() {

        //inject...
        bind(VLayout.class).annotatedWith(Names.named("homePage")).to(HomePage.class);
    }
}

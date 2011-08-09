package mytest.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.name.Named;
import com.smartgwt.client.widgets.layout.VLayout;


@GinModules(MyGinModule.class)
public interface MyInjector extends Ginjector {

    @Named("homePage")
    VLayout getHomePage();

}

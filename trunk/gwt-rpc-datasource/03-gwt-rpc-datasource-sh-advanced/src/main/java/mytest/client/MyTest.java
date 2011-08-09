package mytest.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import mytest.client.gin.MyInjector;

public class MyTest implements EntryPoint {

    public void onModuleLoad() {
        MyInjector injector = GWT.create(MyInjector.class);
        injector.getHomePage().draw();
    }
}

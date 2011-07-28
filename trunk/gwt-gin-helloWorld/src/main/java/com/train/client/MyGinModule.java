package com.train.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.name.Names;
import com.smartgwt.client.widgets.form.DynamicForm;

public class MyGinModule extends AbstractGinModule {

    protected void configure() {
        bind(DynamicForm.class).annotatedWith(Names.named("myFrom")).toProvider(HelloDynamicForm.class);
    }
}

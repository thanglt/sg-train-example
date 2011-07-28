package com.train.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.name.Names;
import com.smartgwt.client.widgets.form.DynamicForm;

public class MyGinModule extends AbstractGinModule {

    protected void configure() {
        //如果能通过改善配置，能省去这句 code，是否有这种可能性？
//        bind(DynamicForm.class).annotatedWith(Names.named("myFrom")).toProvider(HelloDynamicForm.class);
    }
}

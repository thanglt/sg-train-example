package com.train.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class HomePage extends VLayout {

//        @Inject
//    public HomePage(@Named("myFrom") DynamicForm form) {
    @Inject
    public HomePage(HelloDynamicForm3 form , MyButton btn) {
        //ruguo yao yong de hua

        setWidth100();
        setHeight100();
        setBackgroundColor("yellow");

        addMember(form);
        addMember(btn);

    }
}

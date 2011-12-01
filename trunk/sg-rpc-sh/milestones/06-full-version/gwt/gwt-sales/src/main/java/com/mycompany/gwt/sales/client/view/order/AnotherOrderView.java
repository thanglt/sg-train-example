package com.mycompany.gwt.sales.client.view.order;

import com.google.inject.Singleton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: Another Order View for test
 */
public class AnotherOrderView extends VLayout {

    public AnotherOrderView() {
        setWidth100();
        setHeight100();
        setBackgroundColor("yellow");

        Label label = new Label("Another Order View");

        addMember(label);
    }
}

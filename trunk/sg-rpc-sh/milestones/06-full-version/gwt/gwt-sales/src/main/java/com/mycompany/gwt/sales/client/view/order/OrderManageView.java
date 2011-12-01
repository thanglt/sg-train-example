package com.mycompany.gwt.sales.client.view.order;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.OrderDataSource;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
public class OrderManageView extends VLayout {

    @Inject
    public OrderManageView(SelectDsForm selectDsForm, OrderListGrid listGrid,
                           OrderDynamicForm dynamicForm, EditorBtnLayout editorBtnLayout,
                           OrderDataSource orderDS) {
        setWidth100();
        setHeight100();
//        setBackgroundColor("yellow");

        listGrid.setWidth100();
        listGrid.setHeight("30%");
        listGrid.setDataSource(orderDS);

        dynamicForm.setWidth100();
        dynamicForm.setHeight(200);
        dynamicForm.setDataSource(orderDS);

        addMember(selectDsForm);
        addMember(listGrid);
        addMember(dynamicForm);
        addMember(editorBtnLayout);
    }
}

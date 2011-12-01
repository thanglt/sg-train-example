package com.mycompany.gwt.sales.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.name.Named;
import com.mycompany.gwt.sales.client.view.order.AnotherOrderView;
import com.mycompany.gwt.sales.client.view.order.OrderManageView;
import com.mycompany.gwt.sales.client.view.systemManage.assignRole2User.AssignRole2User;
import com.mycompany.gwt.sales.client.view.systemManage.roleManage.RoleManagerView;
import com.mycompany.gwt.sales.client.view.systemManage.userManage.UserManagerView;
import com.smartgwt.client.widgets.layout.Layout;


@GinModules(SalesGinModule.class)
public interface SalesInjector extends Ginjector {

    @Named("homePage")
    Layout getHomePage();

    AnotherOrderView getAnotherView();

    OrderManageView getOrderManageView();

    UserManagerView getUserManageView();

    RoleManagerView getRoleManagerView();

    AssignRole2User getAssignRole2User();
}

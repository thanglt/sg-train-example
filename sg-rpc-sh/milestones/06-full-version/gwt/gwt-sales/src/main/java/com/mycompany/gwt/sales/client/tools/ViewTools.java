package com.mycompany.gwt.sales.client.tools;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mycompany.gwt.sales.client.gin.SalesInjector;
import com.mycompany.gwt.sales.client.view.ViewNameEnum;
import com.mycompany.gwt.sales.client.view.order.AnotherOrderView;
import com.mycompany.gwt.sales.client.view.order.OrderManageView;
import com.mycompany.gwt.sales.client.view.systemManage.assignRole2User.AssignRole2User;
import com.mycompany.gwt.sales.client.view.systemManage.roleManage.RoleManagerView;
import com.mycompany.gwt.sales.client.view.systemManage.userManage.UserManagerView;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
@Singleton
public class ViewTools {

    private SalesInjector injector;

    @Inject
    public ViewTools(SalesInjector injector) {
        this.injector = injector;
    }

    public Layout getView(ViewNameEnum viewName) {
        if (viewName == null) {
            return null;
        }
        switch (viewName) {
            case orderViewOne:
                return injector.getOrderManageView();
            case orderViewTwo:
                return injector.getAnotherView();
            case userManageView:
                return injector.getUserManageView();
            case roleManageView:
                return injector.getRoleManagerView();
            case assignRole2User:
                return injector.getAssignRole2User();
            default:
                break;
        }
        return null;
    }

}

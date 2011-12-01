package com.mycompany.gwt.sales.client.view.systemManage.assignRole2User;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.RoleDataSource;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 11-8-22
 * Description: 已分配角色列表
 */
@Singleton
public class AssignRoleListGrid extends SimpleRoleListGrid{

    @Inject
    public AssignRoleListGrid(RoleDataSource roleDS , SalesConstants constants) {
        setDataSource(roleDS);
        setHeaderSpanName(constants.label_assigned_role());
    }
}

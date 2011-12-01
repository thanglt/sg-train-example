package com.mycompany.gwt.sales.client.view.systemManage.assignRole2User;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.RoleDataSource;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 11-8-22
 * Description: 可分配角色列表
 */
@Singleton
public class ValidRoleListGrid extends SimpleRoleListGrid{

    @Inject
    public ValidRoleListGrid(RoleDataSource roleDS , SalesConstants constants) {
        setDataSource(roleDS);
        setHeaderSpanName(constants.label_valid_role());
    }
}

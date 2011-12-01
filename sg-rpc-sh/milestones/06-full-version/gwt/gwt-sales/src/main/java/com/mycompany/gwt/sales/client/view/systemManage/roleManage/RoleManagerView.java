package com.mycompany.gwt.sales.client.view.systemManage.roleManage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.RoleDataSource;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
public class RoleManagerView extends VLayout {

    @Inject
    public RoleManagerView(final RoleListGrid listGrid,
                           final RoleDynamicForm dynamicForm,
                           EditorBtnLayout editorBtnLayout,
                           RoleDataSource roleDS) {

        setWidth100();
        setHeight100();
//        setBackgroundColor("yellow");

        listGrid.setWidth100();
        listGrid.setHeight(200);

        dynamicForm.setWidth100();
        dynamicForm.setHeight(200);

        listGrid.setDataSource(roleDS);
        dynamicForm.setDataSource(roleDS);
        dynamicForm.getField("isSystem").setDefaultValue(false);//设置 isSystem 默认值

        listGrid.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dynamicForm.editRecord(listGrid.getSelectedRecord());
            }
        });

        addMember(listGrid);
        addMember(dynamicForm);
        addMember(editorBtnLayout);

        listGrid.fetchData();
    }
}

package com.mycompany.gwt.sales.client.view.systemManage.userManage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.UserDataSource;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
public class UserManagerView extends VLayout {

    @Inject
    public UserManagerView(final UserListGrid listGrid,
                           final UserDynamicForm dynamicForm,
                           EditorBtnLayout editorBtnLayout,
                           UserDataSource userDS) {

        setWidth100();
        setHeight100();
//        setBackgroundColor("yellow");

        listGrid.setWidth100();
        listGrid.setHeight(200);

        dynamicForm.setWidth100();
        dynamicForm.setHeight(200);

        listGrid.setDataSource(userDS);
        dynamicForm.setDataSource(userDS);

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

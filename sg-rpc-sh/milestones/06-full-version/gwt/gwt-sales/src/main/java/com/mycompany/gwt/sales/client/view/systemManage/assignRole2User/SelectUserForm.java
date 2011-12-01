package com.mycompany.gwt.sales.client.view.systemManage.assignRole2User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.RoleDataSource;
import com.mycompany.gwt.sales.client.datasource.UserDataSource;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.service.UserServiceRPCAsync;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/21/11
 * Description: 选择用户 from
 */

@Singleton
public class SelectUserForm extends DynamicForm {

    private RoleDataSource roleDS;
    private ValidRoleListGrid validRoleListGrid;
    private AssignRoleListGrid assignRoleListGrid;
    private UserServiceRPCAsync userService;

    @Inject
    public SelectUserForm(final UserDataSource userDS,
                          final RoleDataSource roleDS,
                          final ValidRoleListGrid validRoleListGrid,
                          final AssignRoleListGrid assignRoleListGrid,
                          UserServiceRPCAsync userService,
                          SalesConstants constants) {

        this.roleDS = roleDS;
        this.validRoleListGrid = validRoleListGrid;
        this.assignRoleListGrid = assignRoleListGrid;
        this.userService = userService;

        SelectItem selectItem = new SelectItem("id");
        selectItem.setOptionDataSource(userDS);
        selectItem.setTitle(constants.label_select_user());
        selectItem.setDisplayField("username");
        selectItem.setValueField("id");

        setItems(selectItem);

        //onChanged 事件监听，获取已分配角色列表
        selectItem.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
//                String userId = event.getValue().toString();
                String userId = getValueAsString("id");
                userDS.getServiceAsync().getRoleListByUser(userId, new AsyncCallback<List<RoleDto>>() {
                    public void onFailure(Throwable throwable) {
                        SC.warn(throwable.getMessage());
                    }

                    public void onSuccess(List<RoleDto> roleDtos) {
                        ListGridRecord[] data = new ListGridRecord[roleDtos.size()];
                        for (int i = 0; i < roleDtos.size(); i++) {
                            RoleDto roleDto = roleDtos.get(i);
                            ListGridRecord r = new ListGridRecord();
                            roleDS.copyValues(roleDto, r);
                            data[i] = r;
                        }
                        assignRoleListGrid.setData(data);
                    }
                });
            }
        });

        //onChanged 事件监听，获取可分配角色列表
        selectItem.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
//                String userId = event.getValue().toString();
                String userId = getValueAsString("id");
                userDS.getServiceAsync().getValidRoleListByUser(userId, new AsyncCallback<List<RoleDto>>() {
                    public void onFailure(Throwable throwable) {
                        SC.warn(throwable.getMessage());
                    }

                    public void onSuccess(List<RoleDto> roleDtos) {
                        ListGridRecord[] data = new ListGridRecord[roleDtos.size()];
                        for (int i = 0; i < roleDtos.size(); i++) {
                            RoleDto roleDto = roleDtos.get(i);
                            ListGridRecord r = new ListGridRecord();
                            roleDS.copyValues(roleDto, r);
                            data[i] = r;
                        }
                        validRoleListGrid.setData(data);
                    }
                });
            }
        });

    }

    public void freshData() {
        String userId = getValueAsString("id");
        userService.getRoleListByUser(userId, new AsyncCallback<List<RoleDto>>() {
            public void onFailure(Throwable throwable) {
                SC.warn(throwable.getMessage());
            }

            public void onSuccess(List<RoleDto> roleDtos) {
                ListGridRecord[] data = new ListGridRecord[roleDtos.size()];
                for (int i = 0; i < roleDtos.size(); i++) {
                    RoleDto roleDto = roleDtos.get(i);
                    ListGridRecord r = new ListGridRecord();
                    roleDS.copyValues(roleDto, r);
                    data[i] = r;
                }
                assignRoleListGrid.setData(data);
            }
        });
        userService.getValidRoleListByUser(userId, new AsyncCallback<List<RoleDto>>() {
            public void onFailure(Throwable throwable) {
                SC.warn(throwable.getMessage());
            }

            public void onSuccess(List<RoleDto> roleDtos) {
                ListGridRecord[] data = new ListGridRecord[roleDtos.size()];
                for (int i = 0; i < roleDtos.size(); i++) {
                    RoleDto roleDto = roleDtos.get(i);
                    ListGridRecord r = new ListGridRecord();
                    roleDS.copyValues(roleDto, r);
                    data[i] = r;
                }
                validRoleListGrid.setData(data);
            }
        });
    }
}

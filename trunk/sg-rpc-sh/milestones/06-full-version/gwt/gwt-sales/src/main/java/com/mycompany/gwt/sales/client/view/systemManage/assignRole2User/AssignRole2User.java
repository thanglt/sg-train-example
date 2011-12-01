package com.mycompany.gwt.sales.client.view.systemManage.assignRole2User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mycompany.gwt.sales.client.datasource.UserDataSource;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/19/11
 * Description: 分配角色至账号 主界面
 */
public class AssignRole2User extends VLayout {

    private SelectUserForm selectUserForm;
    private AssignRoleListGrid assignRoleListGrid;
    private UserDataSource userDS;
    private ValidRoleListGrid validRoleListGrid;

    @Inject
    public AssignRole2User(final SelectUserForm selectUserForm,
                           final ValidRoleListGrid validRoleListGrid,
                           final AssignRoleListGrid assignRoleListGrid,
                           final UserDataSource userDS) {

        this.selectUserForm = selectUserForm;
        this.assignRoleListGrid = assignRoleListGrid;
        this.validRoleListGrid = validRoleListGrid;
        this.userDS = userDS;

        validRoleListGrid.addDoubleClickHandler(new DoubleClickHandler() {
            public void onDoubleClick(DoubleClickEvent event) {
                addTransferData();
            }
        });

        assignRoleListGrid.addDoubleClickHandler(new DoubleClickHandler() {
            public void onDoubleClick(DoubleClickEvent event) {
                removeTransferData();
            }
        });

        //选择用户 form
        addMember(selectUserForm);

        HStack hStack = new HStack(10);
        hStack.setHeight(160);

        //可选角色列表
        VStack vStack = new VStack();
        vStack.addMember(validRoleListGrid);
        hStack.addMember(vStack);

        //操作按钮 - 增加
        TransferImgButton addArrowImg = new TransferImgButton(TransferImgButton.RIGHT);
        addArrowImg.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addTransferData();
            }
        });

        //操作按钮 - 移除
        TransferImgButton removeArrowImg = new TransferImgButton(TransferImgButton.LEFT);
        removeArrowImg.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                removeTransferData();
            }
        });

        //操作按钮 - 增加所有(未实现)
        TransferImgButton addAllArrowImg = new TransferImgButton(TransferImgButton.RIGHT_ALL);
        addAllArrowImg.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                validRoleListGrid.selectAllRecords();
                addTransferData();
            }
        });

        //操作按钮 - 移除所有(未实现)
        TransferImgButton removeAllArrowImg = new TransferImgButton(TransferImgButton.LEFT_ALL);
        removeAllArrowImg.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                assignRoleListGrid.selectAllRecords();
                removeTransferData();
            }
        });

        VLayout btnStack = new VLayout();
        btnStack.setHeight100();
        btnStack.setAlign(VerticalAlignment.CENTER);
        btnStack.addMember(addAllArrowImg);
        btnStack.addMember(addArrowImg);
        btnStack.addMember(removeArrowImg);
        btnStack.addMember(removeAllArrowImg);

        hStack.addMember(btnStack);

        //已分配角色列表
        VStack vStack2 = new VStack();
        vStack2.addMember(assignRoleListGrid);
        hStack.addMember(vStack2);

        addMember(hStack);
    }

    //私有方法 - 增加角色
    private void addTransferData() {
        String userId = selectUserForm.getValueAsString("id");
        ListGridRecord[] data = validRoleListGrid.getSelection();
        if (data.length < 1) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            ListGridRecord record = data[i];
            String roleId = record.getAttributeAsString("id");
            userDS.getServiceAsync().assignRoleToUser(roleId, userId, new AsyncCallback<String>() {
                public void onFailure(Throwable throwable) {
                    SC.warn(throwable.getMessage());
                }
                public void onSuccess(String s) {
                    selectUserForm.freshData();
                }
            });
        }
    }

    //私有方法 - 移除角色
    private void removeTransferData() {
        String userId = selectUserForm.getValueAsString("id");
        ListGridRecord[] data = assignRoleListGrid.getSelection();
        if (data.length < 1) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            ListGridRecord record = data[i];
            String roleId = record.getAttributeAsString("id");
            userDS.getServiceAsync().removeRoleFromUser(roleId, userId, new AsyncCallback<String>() {
                public void onFailure(Throwable throwable) {
                    SC.warn(throwable.getMessage());
                }
                public void onSuccess(String s) {
                    selectUserForm.freshData();
                }
            });
        }
    }
}

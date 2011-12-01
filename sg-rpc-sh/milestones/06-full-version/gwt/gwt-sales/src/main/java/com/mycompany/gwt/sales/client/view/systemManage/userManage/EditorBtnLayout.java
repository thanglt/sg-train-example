package com.mycompany.gwt.sales.client.view.systemManage.userManage;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.view.order.OrderDynamicForm;
import com.mycompany.gwt.sales.client.view.order.OrderListGrid;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditorBtnLayout extends VLayout {

    @Inject
    public EditorBtnLayout(final UserListGrid listGrid,
                           final UserDynamicForm dynamicForm,
                           final SalesConstants constants) {
        setWidth100();
        setHeight(300);

        IButton btnFetch = new IButton();
        btnFetch.setTitle(constants.btn_fetch());
        btnFetch.setWidth(200);

        IButton btnReset = new IButton();
        btnReset.setTitle(constants.btn_reset());
        btnReset.setWidth(200);

        final IButton btnAdd = new IButton();
        btnAdd.setTitle(constants.btn_save_need_reset());
        btnAdd.setWidth(200);

        IButton btnUpdate = new IButton();
        btnUpdate.setTitle(constants.btn_update());
        btnUpdate.setWidth(200);

        IButton btnDelete = new IButton();
        btnDelete.setTitle(constants.btn_delete());
        btnDelete.setWidth(200);

        addMember(btnFetch);
        addMember(btnReset);
        addMember(btnAdd);
        addMember(btnUpdate);
        addMember(btnDelete);

        //取数据
        btnFetch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                listGrid.invalidateCache();
                listGrid.fetchData();
            }
        });

        //重置表单
        btnReset.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                dynamicForm.clearValues();
                btnAdd.enable();
            }
        });

        //新增
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                dynamicForm.setSaveOperationType(DSOperationType.ADD);
                dynamicForm.saveData(new DSCallback() {
                    public void execute(DSResponse dsResponse, Object o, DSRequest dsRequest) {
                        dynamicForm.clearValues();
                    }
                });
            }
        });

        //更新
        btnUpdate.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                dynamicForm.setSaveOperationType(DSOperationType.UPDATE);
                dynamicForm.saveData();
            }
        });

        //删除
        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                SC.confirm(constants.msg_confirm_delete(), new BooleanCallback() {
                    public void execute(Boolean value) {
                        if (value) {
                            listGrid.removeSelectedData();
                        }
                    }
                });
            }
        });

        //listgrid record 点击
        listGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent recordClickEvent) {
                dynamicForm.editRecord(recordClickEvent.getRecord());
                btnAdd.disable();
            }
        });

    }
}

package com.mycompany.gwt.sales.client.view.order;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditorBtnLayout extends VLayout {

    @Inject
    public EditorBtnLayout(final OrderListGrid listGrid, final OrderDynamicForm dynamicForm) {
        setWidth100();
        setHeight(300);

        IButton btnFetch = new IButton();
        btnFetch.setTitle("Fetch All");
        btnFetch.setWidth(200);

        IButton btnReset = new IButton();
        btnReset.setTitle("Reset Form");
        btnReset.setWidth(200);

        final IButton btnAdd = new IButton();
        btnAdd.setTitle("Add New (need reset form)");
        btnAdd.setWidth(200);

        IButton btnUpdate = new IButton();
        btnUpdate.setTitle("Update");
        btnUpdate.setWidth(200);

        IButton btnDelete = new IButton();
        btnDelete.setTitle("Delete");
        btnDelete.setWidth(200);

        addMember(btnFetch);
        addMember(btnReset);
        addMember(btnAdd);
        addMember(btnUpdate);
        addMember(btnDelete);

        btnFetch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                listGrid.invalidateCache();
                listGrid.fetchData();
            }
        });

        btnReset.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                dynamicForm.clearValues();
                btnAdd.enable();
            }
        });

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

        btnUpdate.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                dynamicForm.setSaveOperationType(DSOperationType.UPDATE);
                dynamicForm.saveData();
            }
        });

        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                listGrid.removeSelectedData();
            }
        });

        listGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent recordClickEvent) {
                dynamicForm.editRecord(recordClickEvent.getRecord());
                btnAdd.disable();
            }
        });

    }
}

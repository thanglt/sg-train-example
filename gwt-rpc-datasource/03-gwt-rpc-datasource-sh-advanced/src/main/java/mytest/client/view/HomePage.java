package mytest.client.view;

import com.google.inject.Inject;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import mytest.client.datasource.ComputerDataSource;
import mytest.client.datasource.UserDataSource;

public class HomePage extends VLayout {

    @Inject
    public HomePage(final ReuseListGrid listGrid, final ReuseDynamicForm dynamicForm,
                    final UserDataSource userDS , final ComputerDataSource computerDS) {

//        this.listGrid = listGrid;
//        this.dynamicForm = dynamicForm;

        setWidth100();
        setHeight100();
        setBackgroundColor("yellow");

        DynamicForm selectDsForm = new DynamicForm();
        SelectItem dsSelect = new SelectItem();
        dsSelect.setTitle("Select DataSource");
        dsSelect.setValueMap("user", "computer");
        dsSelect.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                String ds = (String) event.getValue();
                if (ds.equalsIgnoreCase("user")) {
                    listGrid.setDataSource(userDS);
                    dynamicForm.setDataSource(userDS);
                    listGrid.fetchData();
                } else if(ds.equalsIgnoreCase("computer")){
                    listGrid.setDataSource(computerDS);
                    dynamicForm.setDataSource(computerDS);
                    listGrid.fetchData();
                }
            }
        });
        selectDsForm.setFields(dsSelect);

        listGrid.setWidth100();
        listGrid.setHeight("30%");

        dynamicForm.setWidth100();
        dynamicForm.setHeight("30%");

        VLayout editorLayout = new VLayout();
        editorLayout.setWidth100();
        editorLayout.setHeight("20%");

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

        editorLayout.addMember(btnFetch);
        editorLayout.addMember(btnReset);
        editorLayout.addMember(btnAdd);
        editorLayout.addMember(btnUpdate);
        editorLayout.addMember(btnDelete);

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
                dynamicForm.saveData();
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

        addMember(selectDsForm);
        addMember(listGrid);
        addMember(dynamicForm);
        addMember(editorLayout);
    }

}

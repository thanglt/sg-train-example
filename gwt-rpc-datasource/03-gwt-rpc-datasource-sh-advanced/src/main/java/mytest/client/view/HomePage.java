package mytest.client.view;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import mytest.client.datasource.ComputerDataSource;
import mytest.client.datasource.UserDataSource;

public class HomePage extends VLayout {

//    @Inject
//    private UserDataSource userDS;
//    @Inject
//    private ComputerDataSource computerDS;

    private ListGrid listGrid;
    private DynamicForm dynamicForm;

    @Inject
    public HomePage(final ReuseListGrid listGrid, final ReuseDynamicForm dynamicForm,
                    final UserDataSource userDS , final ComputerDataSource computerDS) {

        this.listGrid = listGrid;
        this.dynamicForm = dynamicForm;

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

        btnFetch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {

            }
        });

        addMember(selectDsForm);
        addMember(listGrid);
        addMember(dynamicForm);
        addMember(editorLayout);
    }

}

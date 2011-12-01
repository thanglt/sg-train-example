package mytest.client.view.sample1;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import mytest.client.datasource.ComputerDataSource;
import mytest.client.datasource.UserDataSource;

public class SelectDsForm extends DynamicForm{

    @Inject
    public SelectDsForm(final ReuseListGrid listGrid, final ReuseDynamicForm dynamicForm,
            final UserDataSource userDS , final ComputerDataSource computerDS) {

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
        setFields(dsSelect);
    }
}

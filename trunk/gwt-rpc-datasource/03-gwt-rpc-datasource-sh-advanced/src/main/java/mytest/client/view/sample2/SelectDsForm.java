package mytest.client.view.sample2;

import com.google.inject.Inject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import mytest.client.datasource.MenuNodeDataSource;

public class SelectDsForm extends DynamicForm {

    @Inject
    public SelectDsForm(final MyTreeGrid myTreeGrid, MenuNodeDataSource menuNodeDS) {

        SelectItem dsSelect = new SelectItem();
        dsSelect.setTitle("Select DataSource Category");
        dsSelect.setValueMap("testTree", "testTree2");

        myTreeGrid.setDataSource(menuNodeDS);

        dsSelect.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                String value = (String) event.getValue();
                if (value.equalsIgnoreCase("testTree")) {
                    myTreeGrid.invalidateCache();
                    myTreeGrid.fetchData(new Criteria("category", "testTree"));
                } else if (value.equalsIgnoreCase("testTree2")) {
                    myTreeGrid.invalidateCache();
                    myTreeGrid.fetchData(new Criteria("category", "testTree2"));
                }
            }
        });
        setFields(dsSelect);
    }
}

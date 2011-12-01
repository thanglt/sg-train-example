package com.mycompany.gwt.sales.client.view.order;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.OrderDataSource;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

@Singleton
public class SelectDsForm extends DynamicForm {

    @Inject
    public SelectDsForm(final OrderListGrid listGrid, final OrderDynamicForm dynamicForm,
                        final OrderDataSource orderDS) {

        SelectItem dsSelect = new SelectItem();
        dsSelect.setTitle("Select DataSource");
        dsSelect.setValueMap("order");

        dsSelect.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                String ds = (String) event.getValue();
                if (ds.equalsIgnoreCase("order")) {
                    listGrid.setDataSource(orderDS);
                    dynamicForm.setDataSource(orderDS);
                    listGrid.fetchData();
                }
            }
        });
        setFields(dsSelect);
    }
}

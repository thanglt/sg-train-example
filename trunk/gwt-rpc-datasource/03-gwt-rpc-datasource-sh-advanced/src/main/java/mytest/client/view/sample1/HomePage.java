package mytest.client.view.sample1;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.layout.VLayout;

public class HomePage extends VLayout {

    @Inject
    public HomePage(SelectDsForm selectDsForm, ReuseListGrid listGrid,
                    ReuseDynamicForm dynamicForm, EditorBtnLayout editorBtnLayout) {

        setWidth100();
        setHeight100();
        setBackgroundColor("yellow");

        listGrid.setWidth100();
        listGrid.setHeight("30%");

        dynamicForm.setWidth100();
        dynamicForm.setHeight("20%");

        addMember(selectDsForm);
        addMember(listGrid);
        addMember(dynamicForm);
        addMember(editorBtnLayout);
    }

}

package mytest.client.view.sample2;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.layout.HLayout;

public class HomePage2 extends HLayout {

    @Inject
    public HomePage2(MyTreeGrid myTreeGrid, SelectDsForm selectDsForm) {

        addMember(myTreeGrid);
        addMember(selectDsForm);
    }

}

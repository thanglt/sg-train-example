package mytest.client.view;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import mytest.client.view.sample1.HomePage;
import mytest.client.view.sample2.HomePage2;


public class MainTabSet extends VLayout {

    @Inject
    public MainTabSet(HomePage homePage , HomePage2 homePage2) {

        setWidth100();
        setHeight100();

        TabSet tabSet = new TabSet();
        tabSet.setWidth100();
        tabSet.setHeight100();

        Tab tab = new Tab();
        tab.setPane(homePage);
        tab.setTitle("Reuse ListGrid");

        Tab tab2 = new Tab();
        tab2.setPane(homePage2);
        tab2.setTitle("Reuse TreeGrid");

        tabSet.setTabs(tab,tab2);

        addMember(tabSet);
    }
}

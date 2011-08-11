package mytest.client.view.sample3;

import com.google.inject.Inject;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import mytest.client.datasource.MenuNodeDataSource;

public class HomePage3 extends VLayout {

    @Inject
    public HomePage3(MenuNodeDataSource menuNodeDS) {

        Canvas main = new Canvas();

        Menu menu = new Menu();
        menu.setCanSelectParentItems(false);
        menu.setDataSource(SupplyCategoryXmlDS.getInstance());
        menu.setWidth(130);
        menu.setShowSubmenus(false);

        menu.addItemClickHandler(new ItemClickHandler() {
            public void onItemClick(ItemClickEvent event) {
                MenuItem item = event.getItem();
                SC.say("You picked the \""
                        + item.getAttributeAsString("categoryName")
                        + "\" category.");
            }
        });

        IMenuButton bCategory = new IMenuButton("Go to category", menu);
        bCategory.setTop(30);
        bCategory.setWidth(140);

        main.addChild(bCategory);

        main.draw();
    }

}

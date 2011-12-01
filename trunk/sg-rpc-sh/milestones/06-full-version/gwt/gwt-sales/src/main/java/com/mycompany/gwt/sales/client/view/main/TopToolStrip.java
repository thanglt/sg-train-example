package com.mycompany.gwt.sales.client.view.main;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.mycompany.gwt.sales.client.event.ModuleSelectedEvent;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.tools.UserTools;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class TopToolStrip extends ToolStrip {

    @Inject
    public TopToolStrip(SalesConstants constants, final EventBus eventBus) {
        setWidth100();
        setHeight100();

        //menuButton 1
        ToolStripMenuButton menuButton1 = new ToolStripMenuButton(constants.organization_manage());
        Menu menu1 = new Menu();
        menu1.setShowShadow(true);
        menu1.setShadowDepth(5);

        MenuItem menuItem1 = new MenuItem(constants.menuItem1_1(), "icons/application_add.png");
        menuItem1.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent menuItemClickEvent) {
                eventBus.fireEvent(new ModuleSelectedEvent("testTree"));
            }
        });
        menu1.addItem(menuItem1);

        MenuItem menuItem2 = new MenuItem(constants.menuItem1_2(), "icons/application_go.png");
        menuItem2.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent menuItemClickEvent) {
                eventBus.fireEvent(new ModuleSelectedEvent("testTree2"));
            }
        });
        menu1.addItem(menuItem2);

        menuButton1.setMenu(menu1);
        if (UserTools.hasRole("ROLE_USER")) {
            addMenuButton(menuButton1);
            addSeparator();
        }

        //menuButton 2
        ToolStripMenuButton menuButton2 = new ToolStripMenuButton(constants.sparePart_manage());
        Menu menu2 = new Menu();
        menu2.addItem(new MenuItem(constants.menuItem2_1(), "icons/application_tile_horizontal.png"));
        if (UserTools.hasResource("test003")) {
            menu2.addItem(new MenuItem(constants.menuItem2_2(), "icons/arrow_inout.png"));
        }
        menuButton2.setMenu(menu2);

        if (UserTools.hasRole("ROLE_USER")) {
            addMenuButton(menuButton2);
            addSeparator();
        }

        //system config
        ToolStripMenuButton systemConfig = new ToolStripMenuButton(constants.menu_system_config());
        Menu menu3 = new Menu();
        MenuItem userManage = new MenuItem(constants.menu_security_manage(), "icons/key.png");
        userManage.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                eventBus.fireEvent(new ModuleSelectedEvent("userManage"));
            }
        });

        menu3.addItem(userManage);
        menu3.addItem(new MenuItem(constants.menuItem3_2(), "icons/bullet_blue.png"));
        menu3.addItem(new MenuItem(constants.menuItem3_3(), "icons/coins_add.png"));
        systemConfig.setMenu(menu3);

        if (UserTools.hasRole("ROLE_ADMIN")) {
            addMenuButton(systemConfig);
            addSeparator();
        }

        //...

    }
}

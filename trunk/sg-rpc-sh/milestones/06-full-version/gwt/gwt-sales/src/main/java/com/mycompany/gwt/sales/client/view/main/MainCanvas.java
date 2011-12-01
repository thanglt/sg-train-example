package com.mycompany.gwt.sales.client.view.main;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.event.TreeNodeSelectedEvent;
import com.mycompany.gwt.sales.client.event.TreeNodeSelectedEventHandler;
import com.mycompany.gwt.sales.client.gin.SalesInjector;
import com.mycompany.gwt.sales.client.tools.ViewTools;
import com.mycompany.gwt.sales.client.view.ViewNameEnum;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.CloseClickHandler;
import com.smartgwt.client.widgets.tab.events.TabCloseClickEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: 用来放置 tab set
 */
@Singleton
public class MainCanvas extends Canvas {

    private TabSet mainTabSet;

    private ViewTools viewTools;
    private SalesInjector injector;

    @Inject
    public MainCanvas(EventBus eventBus, ViewTools viewTools ,
                      SalesInjector injector) {

        this.injector = injector;

        this.viewTools = viewTools;
        setWidth100();
        setHeight100();

        mainTabSet = new TabSet();
        mainTabSet.setWidth100();
        mainTabSet.setHeight100();

        eventBus.addHandler(TreeNodeSelectedEvent.TYPE, new TreeNodeSelectedEventHandler() {
            public void onEvent(TreeNodeSelectedEvent event) {
                if (isOpenedTab(event)) {
                    return;
                } else {
                    createNewTab(event);
                }
            }
        });
        addChild(mainTabSet);
    }

    //判断 tab 是否已经打开
    private boolean isOpenedTab(TreeNodeSelectedEvent event) {
        String viewId = event.viewId;

        Tab[] tabs = mainTabSet.getTabs();
        for (Tab tab : tabs) {
            if (tab.getID().equals(viewId)) {
                mainTabSet.selectTab(tab);
                return true;
            }
        }
        return false;
    }

    //创建新 tab 并选中
    private void createNewTab(TreeNodeSelectedEvent event) {
        String nodeName = event.nodeName;
        String viewId = event.viewId;
        String icon = event.icon;

        Layout layout = viewTools.getView(ViewNameEnum.valueOf(viewId));

        Tab tab = new Tab();
        tab.setID(viewId);
        tab.setTitle("<span>" + Canvas.imgHTML(icon) + "&nbsp;" + nodeName + "</span>");
        tab.setPane(layout);

        tab.setCanClose(true);

        mainTabSet.addTab(tab);
        mainTabSet.selectTab(tab);
    }
}

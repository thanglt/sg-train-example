/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import com.smartgwt.sample.showcase.client.data.CommandTreeNode;
import com.smartgwt.sample.showcase.client.data.ExplorerTreeNode;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Showcase implements EntryPoint, HistoryListener {
    private TabSet mainTabSet;
    private SideNavTree sideNav;
    private Menu contextMenu;

    public void onModuleLoad() {

        final String initToken = History.getToken();

        //setup overall layout / viewport
        VLayout main = new VLayout() {
            protected void onInit() {
                super.onInit();
                if (initToken.length() != 0) {
                    onHistoryChanged(initToken);
                }
            }
        };

        main.setWidth100();
        main.setHeight100();
        main.setLayoutMargin(5);
        main.setStyleName("tabSetContainer");

        HLayout hLayout = new HLayout();
        hLayout.setWidth100();
        hLayout.setHeight100();

        VLayout sideNavLayout = new VLayout();
        sideNavLayout.setHeight100();
        sideNavLayout.setWidth(290);
        sideNavLayout.setShowResizeBar(true);

        sideNav = new SideNavTree();


        sideNav.addLeafClickHandler(new LeafClickHandler() {
            public void onLeafClick(LeafClickEvent event) {
                TreeNode node = event.getLeaf();
                showSample(node);
            }
        });

        sideNavLayout.addMember(sideNav);
        hLayout.addMember(sideNavLayout);

        mainTabSet = new TabSet();
        mainTabSet.setWidth100();
        mainTabSet.setHeight100();

        Layout paneContainerProperties = new Layout();
        paneContainerProperties.setLayoutMargin(0);
        paneContainerProperties.setLayoutTopMargin(1);
        mainTabSet.setPaneContainerProperties(paneContainerProperties);

        mainTabSet.setWidth100();
        mainTabSet.setHeight100();
        mainTabSet.addTabSelectedHandler(new TabSelectedHandler() {
            public void onTabSelected(TabSelectedEvent event) {
                Tab selectedTab = event.getTab();
                String historyToken = selectedTab.getAttribute("historyToken");
                if (historyToken != null) {
                    History.newItem(historyToken, false);
                } else {
                    History.newItem("main", false);
                }
            }
        });

        Tab tab = new Tab();
        tab.setTitle("SmartGWT EE Showcase&nbsp;&nbsp;");
        tab.setIcon("silk/database_connect.png");

        ShowcaseGrid tocGrid = new ShowcaseGrid();
        tocGrid.setHeight100();
        tocGrid.setWidth100();

        tocGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
            public void onRecordDoubleClick(RecordDoubleClickEvent event) {
                TreeNode node = (TreeNode) event.getRecord();
                showSample(node);
            }
        });

        Window window = new Window();
        window.setTitle("SmartGWT Enterprise Edition Showcase");
        window.setHeaderIcon("silk/database_connect.png", 16, 16);
        window.setWidth(500);
        window.setHeight(375);
        window.addItem(tocGrid);
        window.setKeepInParentRect(true);
        window.setTop(30);
        window.setLeft(30);
        window.setCanDragResize(true);


        HLayout mainPanel = new HLayout();
        mainPanel.setHeight100();
        mainPanel.setWidth100();

        if (SC.hasFirebug()) {
            Label label = new Label();
            label.setContents("<p>Firebug can make the Showcase run slow.</p><p>For the best performance, we suggest disabling Firebug for this site.</p>");
            label.setWidth100();
            label.setHeight("auto");
            label.setMargin(20);
            Window fbWindow = new Window();
            fbWindow.setHeaderIcon("silk/bug.png", 16, 16);
            fbWindow.setTitle("Firebug Detected");
            fbWindow.addItem(label);
            fbWindow.setWidth(220);
            fbWindow.setHeight(130);

            LayoutSpacer spacer = new LayoutSpacer();
            spacer.setWidth100();
            mainPanel.addMember(spacer);
            mainPanel.addMember(fbWindow);
        }
        mainPanel.addChild(window);

        contextMenu = createContextMenu();
        tab.setPane(mainPanel);

        mainTabSet.addTab(tab);

        Canvas canvas = new Canvas();
        canvas.setBackgroundImage("[SKIN]/shared/background.gif");
        canvas.setWidth100();
        canvas.setHeight100();
        canvas.addChild(mainTabSet);

        hLayout.addMember(canvas);
        main.addMember(hLayout);
        main.draw();

        // Add history listener
        History.addHistoryListener(this);

        RootPanel.get("loadingMsg").getElement().setInnerHTML("");
    }

    private Menu createContextMenu() {
        Menu menu = new Menu();
        menu.setWidth(140);

        MenuItemIfFunction enableCondition = new MenuItemIfFunction() {
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                int selectedTab = mainTabSet.getSelectedTabNumber();
                return selectedTab != 0;
            }
        };

        MenuItem closeItem = new MenuItem("<u>C</u>lose");
        closeItem.setEnableIfCondition(enableCondition);
        closeItem.setKeyTitle("Alt+C");
        KeyIdentifier closeKey = new KeyIdentifier();
        closeKey.setAltKey(true);
        closeKey.setKeyName("C");
        closeItem.setKeys(closeKey);
        closeItem.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                int selectedTab = mainTabSet.getSelectedTabNumber();
                mainTabSet.removeTab(selectedTab);
                mainTabSet.selectTab(selectedTab - 1);
            }
        });

        MenuItem closeAllButCurrent = new MenuItem("Close All But Current");
        closeAllButCurrent.setEnableIfCondition(enableCondition);
        closeAllButCurrent.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                int selected = mainTabSet.getSelectedTabNumber();
                Tab[] tabs = mainTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 2];
                int cnt = 0;
                for (int i = 1; i < tabs.length; i++) {
                    if (i != selected) {
                        tabsToRemove[cnt] = i;
                        cnt++;
                    }
                }
                mainTabSet.removeTabs(tabsToRemove);
            }
        });

        MenuItem closeAll = new MenuItem("Close All");
        closeAll.setEnableIfCondition(enableCondition);
        closeAll.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                Tab[] tabs = mainTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 1];

                for (int i = 1; i < tabs.length; i++) {
                    tabsToRemove[i - 1] = i;
                }
                mainTabSet.removeTabs(tabsToRemove);
                mainTabSet.selectTab(0);
            }
        });

        menu.setItems(closeItem, closeAllButCurrent, closeAll);
        return menu;
    }

    protected void showSample(TreeNode node) {
        boolean isExplorerTreeNode = node instanceof ExplorerTreeNode;
        if (node instanceof CommandTreeNode) {
            CommandTreeNode commandTreeNode = (CommandTreeNode) node;
            commandTreeNode.getCommand().execute();
        } else if (isExplorerTreeNode) {
            ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode) node;
            PanelFactory factory = explorerTreeNode.getFactory();
            if (factory != null) {
                String panelID = factory.getID();
                Tab tab = null;
                if (panelID != null) {
                    String tabID = panelID + "_tab";
                    tab = mainTabSet.getTab(tabID);
                }
                if (tab == null) {
                    Canvas panel = factory.create();
                    tab = new Tab();
                    tab.setID(factory.getID() + "_tab");
                    //store history token on tab so that when an already open is selected, one can retrieve the
                    //history token and update the URL
                    tab.setAttribute("historyToken", explorerTreeNode.getNodeID());
                    tab.setContextMenu(contextMenu);

                    String sampleName = explorerTreeNode.getName();


                    String icon = explorerTreeNode.getIcon();
                    if (icon == null) {
                        icon = "silk/plugin.png";
                    }
                    String imgHTML = Canvas.imgHTML(icon, 16, 16);
                    tab.setTitle("<span>" + imgHTML + "&nbsp;" + sampleName + "</span>");
                    tab.setPane(panel);
                    tab.setCanClose(true);
                    mainTabSet.addTab(tab);
                    mainTabSet.selectTab(tab);
                } else {
                    mainTabSet.selectTab(tab);
                }
                History.newItem(explorerTreeNode.getNodeID(), false);
            }
        }
    }

    public void onHistoryChanged(String historyToken) {
        if (historyToken == null || historyToken.equals("")) {
            mainTabSet.selectTab(0);
        } else {
            ExplorerTreeNode[] showcaseData = sideNav.getShowcaseData();
            for (ExplorerTreeNode explorerTreeNode : showcaseData) {
                if (explorerTreeNode.getNodeID().equals(historyToken)) {
                    showSample(explorerTreeNode);
                    sideNav.selectRecord(explorerTreeNode);
                    Tree tree = sideNav.getData();
                    TreeNode categoryNode = tree.getParent(explorerTreeNode);

                    while (categoryNode != null && !"/".equals(tree.getName(categoryNode))) {
                        tree.openFolder(categoryNode);
                        categoryNode = tree.getParent(categoryNode);
                    }
                }
            }
        }
    }

}

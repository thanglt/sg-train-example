package com.train.client.Accounts;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickEvent;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickHandler;
import com.train.client.ContextAreaFactory;
import com.train.client.data.NavigationPaneRecord;
import com.train.client.data.ResourceCentreNavigationPaneSectionData;
import com.train.client.data.SalesNavigationPaneSectionData;
import com.train.client.data.SettingsNavigationPaneSectionData;
import com.train.client.welcome.Masthead;
import com.train.client.welcome.NavigationPane;
import com.train.client.welcome.NavigationPaneSection;

public class AccountEntryPoint implements EntryPoint {
    private static final int NORTH_HEIGHT = 73; // TOOLBAR_HEIGHT + MASTHEAD_HEIGHT

    private static final String DEFAULT_MARGIN = "0px";

    private VLayout mainLayout;
    private HLayout northLayout;
    private HLayout southLayout;
    private VLayout eastLayout;
    private VLayout westLayout;

    private ToolBar toolbar;

    interface GlobalResources extends ClientBundle {
        @CssResource.NotStrict
        @Source("myGwt.css")
        CssResource css();
    }

    public void onModuleLoad() {

        GWT.log("init onModuleLoad()...", null);

        // inject global styles
        GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();

        // get rid of scroll bars, and clear out the window's built-in margin,
        // because we want to take advantage of the entire client area
        Window.enableScrolling(false);
        Window.setMargin(DEFAULT_MARGIN);

        // initialise the main layout container
        mainLayout = new VLayout();
        mainLayout.setWidth100();
        mainLayout.setHeight100();

        mainLayout.addStyleName("crm-Entity-MainLayout");

        // initialise the North layout container
        northLayout = new HLayout();
        northLayout.setHeight(NORTH_HEIGHT);

        VLayout vLayout = new VLayout();

        // initialise the Application menu
        toolbar = new ToolBar();

        // add the ToolBar and the Masthead to the nested layout container
        vLayout.addMember(toolbar);
        vLayout.addMember(new Masthead());

        // add the nested layout container to the North layout container
        northLayout.addMember(vLayout);

        // initialise the Navigation Pane
        NavigationPane navigationPane = new NavigationPane();
//        navigationPane.add("Details", DetailsNavigationPaneSectionData.getRecords(),
//                new NavigationPaneClickHandler());
        navigationPane.add("Sales", SalesNavigationPaneSectionData.getRecords(),
                new NavigationPaneClickHandler());
        navigationPane.add("Settings", SettingsNavigationPaneSectionData.getRecords(),
                new NavigationPaneClickHandler());
        navigationPane.add("Resource Centre", ResourceCentreNavigationPaneSectionData.getRecords(),
                new NavigationPaneClickHandler());

        // select the first Navigation Pane section e.g Details
        navigationPane.expandSection(0);

        // initialise the West layout container
        westLayout = navigationPane;

        // initialise the East layout container
//        eastLayout = new InformationView();
        eastLayout = new AccountsView();

        // initialise the South layout container
        southLayout = new HLayout();

        // set the Navigation Pane and Context Area as members of the South layout container
        southLayout.setMembers(westLayout, eastLayout);

        // add the North and South layout containers to the main layout container
        mainLayout.addMember(northLayout);
        mainLayout.addMember(southLayout);

        // add the main layout container to GWT's root panel
        RootLayoutPanel.get().add(mainLayout);
    }

    public class NavigationPaneHeaderClickHandler implements SectionHeaderClickHandler {
        public void onSectionHeaderClick(SectionHeaderClickEvent event) {
            // get the Section Stack section's name e.g. "Sales"
            NavigationPaneSection section = (NavigationPaneSection) event.getSection();

            // get the selected record from the Section Stack section's List Grid
            NavigationPaneRecord record = (NavigationPaneRecord) section.getListGrid().getSelectedRecord();

            setContextAreaView(record);
        }
    }

    private class NavigationPaneClickHandler implements RecordClickHandler {
        public void onRecordClick(RecordClickEvent event) {
            NavigationPaneRecord record = (NavigationPaneRecord) event.getRecord();
            setContextAreaView(record);
        }
    }

    private void setContextAreaView(NavigationPaneRecord record) {
        ContextAreaFactory factory = record.getFactory();
        Canvas view = factory.create();
        southLayout.setMembers(westLayout, view);
    }
}

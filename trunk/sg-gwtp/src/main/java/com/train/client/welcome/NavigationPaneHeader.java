package com.train.client.welcome;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/2/11
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationPaneHeader extends HLayout {
    private static final int WEST_WIDTH = 200;
    private static final String NAVIGATION_PANE_HEADER_HEIGHT = "27px";
    private static final String NAVIGATION_PANE_HEADER_LABEL_DISPLAY_NAME = "Workplace";
    private static final String CONTEXT_AREA_HEADER_LABEL_DISPLAY_NAME = "Activities";

    private Label navigationPaneHeaderLabel;
    private Label contextAreaHeaderLabel;

    public NavigationPaneHeader() {
        super();

        GWT.log("init NavigationPaneHeader()...", null);

        // initialise the Navigation Pane Header layout container
        this.addStyleName("crm-NavigationPane-Header");
        this.setHeight(NAVIGATION_PANE_HEADER_HEIGHT);

        // initialise the Navigation Pane Header Label
        navigationPaneHeaderLabel = new Label();
        navigationPaneHeaderLabel.addStyleName("crm-NavigationPane-Header-Label");
        navigationPaneHeaderLabel.setWidth(WEST_WIDTH);
        navigationPaneHeaderLabel.setContents(NAVIGATION_PANE_HEADER_LABEL_DISPLAY_NAME);
        navigationPaneHeaderLabel.setAlign(Alignment.LEFT);
        navigationPaneHeaderLabel.setOverflow(Overflow.HIDDEN);

        // initialise the Context Area Header Label
        contextAreaHeaderLabel = new Label();
        contextAreaHeaderLabel.addStyleName("crm-ContextArea-Header-Label");
        contextAreaHeaderLabel.setContents(CONTEXT_AREA_HEADER_LABEL_DISPLAY_NAME);
        contextAreaHeaderLabel.setAlign(Alignment.LEFT);
        contextAreaHeaderLabel.setOverflow(Overflow.HIDDEN);

        // add the Labels to the Navigation Pane Header layout container
        this.addMember(navigationPaneHeaderLabel);
        this.addMember(contextAreaHeaderLabel);
    }

    public Label getNavigationPaneHeaderLabel() {
        return navigationPaneHeaderLabel;
    }

    public Label getContextAreaHeaderLabel() {
        return contextAreaHeaderLabel;
    }

    public void setNavigationPaneHeaderLabelContents(String contents) {
        navigationPaneHeaderLabel.setContents(contents);
    }

    public String getNavigationPaneHeaderLabelContents() {
        return navigationPaneHeaderLabel.getContents();
    }

    public void setContextAreaHeaderLabelContents(String contents) {
        contextAreaHeaderLabel.setContents(contents);
    }

    public String getContextAreaHeaderLabelContents() {
        return contextAreaHeaderLabel.getContents();
    }


}

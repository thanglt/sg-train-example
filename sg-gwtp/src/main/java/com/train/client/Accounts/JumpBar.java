package com.train.client.Accounts;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/2/11
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class JumpBar extends HLayout {

    private static final String JUMPBAR_HEIGHT = "23px";
    private static final String ALL_LABEL_DISPLAY_NAME = "All";

    public JumpBar() {
        super();

        GWT.log("init JumpBar()...", null);

        // initialise the JumpBar layout container
        this.addStyleName("crm-JumpBar");
        this.setHeight(JUMPBAR_HEIGHT);

        // initialise the LayoutSpacer
        LayoutSpacer paddingLeft = new LayoutSpacer();
        paddingLeft.setWidth(8);
        this.addMember(paddingLeft);

        // initialise the All label
        Label allLabel = new Label();
        // allLabel.addStyleName("crm-JumpBar-Label");
        allLabel.setBaseStyle("crm-JumpBar-Label");
        allLabel.setContents(ALL_LABEL_DISPLAY_NAME);
        allLabel.setAlign(Alignment.LEFT);
        allLabel.setOverflow(Overflow.HIDDEN);
        allLabel.setWidth(24);
        this.addMember(allLabel);

        // initialise the LayoutSpacer
        LayoutSpacer allLabelPadding = new LayoutSpacer();
        allLabelPadding.setWidth("*");
        this.addMember(allLabelPadding);

        String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
        String labelContents = "A";

        for (int i = 0; i < 27; i++) {
            Label label = new Label();
            // allLabel.addStyleName("crm-JumpBar-Label");
            label.setBaseStyle("crm-JumpBar-Label");
            labelContents = alphabet.substring(i, i + 1);
            label.setContents(labelContents);
            label.setAlign(Alignment.CENTER);
            label.setOverflow(Overflow.HIDDEN);
            label.setWidth(20);

            this.addMember(label);

            if (i < 26) {
                LayoutSpacer padding = new LayoutSpacer();
                padding.setWidth("*");
                this.addMember(padding);
            }
        }

        // add the LayoutSpacer to the JumpBar layout container
        LayoutSpacer paddingRight = new LayoutSpacer();
        paddingRight.setWidth(8);
        this.addMember(paddingRight);
    }
}

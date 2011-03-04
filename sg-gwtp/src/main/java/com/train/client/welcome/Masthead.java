package com.train.client.welcome;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.train.client.Messages;


/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Masthead extends HLayout {

    private final Messages msg = GWT.create(Messages.class);
    private static final int MASTHEAD_HEIGHT = 60;
    private static final String MASTHEAD_BACKGROUNDCOLOR = "#C3D9FF";

    Label label;

    public Masthead() {
        super();

        GWT.log("init Masthead()...", null);

        // initialise the Masthead layout container
        this.addStyleName("crm-Masthead");
        this.setHeight(MASTHEAD_HEIGHT);

        // initialise the Logo image
        Img logo = new Img("logo.png", 48, 48);
        logo.addStyleName("crm-Masthead-Logo");

        // initialise the Name label
        Label name = new Label();
        name.addStyleName("crm-MastHead-Name");
        name.setContents("myCRM");

        // initialise the West layout container
        HLayout westLayout = new HLayout();
        westLayout.setHeight(MASTHEAD_HEIGHT);
        westLayout.setWidth("50%");
        westLayout.addMember(logo);
        westLayout.addMember(name);

        // initialise the Signed In User label
        Label signedInUser = new Label();
        signedInUser.addStyleName("crm-MastHead-SignedInUser");
        signedInUser.setContents("<b>Rob Ferguson</b><br />upTick");

        // initialise the East layout container
        HLayout eastLayout = new HLayout();
        eastLayout.setAlign(Alignment.RIGHT);
        eastLayout.setHeight(MASTHEAD_HEIGHT);
        eastLayout.setWidth("50%");
        eastLayout.addMember(signedInUser);

        // add the West and East layout containers to the Masthead layout container
        this.addMember(westLayout);
        this.addMember(eastLayout);
    }
}

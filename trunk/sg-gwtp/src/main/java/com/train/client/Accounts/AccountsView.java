package com.train.client.Accounts;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.train.client.ContextAreaFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountsView extends VLayout {

    private static final String DESCRIPTION = "AccountsView";
    private static final String CONTEXT_AREA_WIDTH = "*";

    public AccountsView() {
        super();

        GWT.log("init AccountsView()...", null);

        // initialise the Accounts View layout container
        this.addStyleName("crm-ContextArea");
        this.setWidth(CONTEXT_AREA_WIDTH);

        // add the List Grid to the Accounts View layout container
        this.addMember(new ToolBar());
        this.addMember(new AccountsListGrid());
        this.addMember(new StatusBar());
        this.addMember(new JumpBar());
    }

    public static class Factory implements ContextAreaFactory {

        private String id;

        public Canvas create() {
            AccountsView view = new AccountsView();
            id = view.getID();

            GWT.log("AccountsView.Factory.create()->view.getID() - " + id, null);
            return view;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
        }
    }

}

package com.mycompany.gwt.sales.client.view.main;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.i18n.SalesMessages;
import com.mycompany.gwt.sales.client.service.UserServiceRPCAsync;
import com.mycompany.gwt.sales.client.tools.UserTools;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * 用户登出面板
 */
public class LogoutToolStrip extends ToolStrip {

    @Inject
    public LogoutToolStrip(final SalesConstants constants , SalesMessages messages) {
        setWidth(100);
        setHeight100();

        Label label = new Label();
        label.setAlign(Alignment.CENTER);
        label.setContents(messages.msg_welcome(UserTools.loginUser.getName()));
        addMember(label);

        addSeparator();

        IButton btn = new IButton();
        btn.setIcon("icons/exit_start.png");
        btn.setTitle(constants.label_logout());

        btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                SC.confirm(constants.msg_confirm_logout(), new BooleanCallback() {
                    public void execute(Boolean value) {
                        if (value) {
                            Window.open("./j_spring_security_logout", "_self", null);
                        }
                    }
                });
            }
        });

        addMember(btn);
    }
}

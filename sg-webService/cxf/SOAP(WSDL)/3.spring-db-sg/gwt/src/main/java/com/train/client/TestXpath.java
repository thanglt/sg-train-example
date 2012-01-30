package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.data.XMLTools;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-10-25
 * Time: 上午11:05
 * Description: Test Xpath usage...
 */
public class TestXpath implements EntryPoint {
    public void onModuleLoad() {

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.setBackgroundColor("yellow");

        //case 1
        IButton btn1 = new IButton("case 1");
        btn1.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String s = XMLTools.selectString("<accountlist><account><acct_id>1</acct_id></account></accountlist>", "//acct_id");
                SC.say("result : " + s);
            }
        });
        layout.addMember(btn1);

        //case 2
        IButton btn2 = new IButton("case 2");
        btn2.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String s = XMLTools.selectString("<accountlist><account><acct_id>1</acct_id></account>" +
                        "<account><acct_id>2</acct_id></account></accountlist>", "//acct_id");
                SC.say("result : " + s);
            }
        });
        layout.addMember(btn2);

        //case 3
        IButton btn3 = new IButton("case 6");
        btn3.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String s = XMLTools.selectString("<soap:Envelope><soap:Body><ns2:CreateResponse>" +
                        "<OperationOutputType>" +
                        "<success>true</success>" +
                        "<responseText>record created</responseText>" +
                        "</OperationOutputType>" +
                        "</ns2:CreateResponse></soap:Body></soap:Envelope>", "//responseText");
                SC.say("result : " + s);
            }
        });
        layout.addMember(btn3);

        //case 4
        IButton btn4 = new IButton("case 7");
        btn4.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String s = XMLTools.selectString("<Envelope><Body><CreateResponse>" +
                        "<OperationOutputType>" +
                        "<success>true</success>" +
                        "<responseText>record created</responseText>" +
                        "</OperationOutputType>" +
                        "</CreateResponse></Body></Envelope>", "//success");
                SC.say("result : " + s);
            }
        });
        layout.addMember(btn4);

        layout.draw();

        //To change body of implemented methods use File | Settings | File Templates.
    }
}

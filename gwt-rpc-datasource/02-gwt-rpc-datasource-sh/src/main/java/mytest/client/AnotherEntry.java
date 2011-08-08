package mytest.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.layout.VLayout;
//import mytest.client.service.UserServiceRPC;
//import mytest.client.service.UserServiceRPCAsync;

public class AnotherEntry implements EntryPoint {

//    UserServiceRPCAsync service = GWT.create(UserServiceRPC.class);

    public void onModuleLoad() {

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.setBackgroundColor("yellow");

//        IButton btn = new IButton();
//        btn.setTitle("click me");
//        btn.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent clickEvent) {
//                service.getAll(new AsyncCallback<List<UserDto>>() {
//                    public void onFailure(Throwable caught) {
//                        SC.warn(caught.getMessage());
//                    }
//                    public void onSuccess(List<UserDto> result) {
//                        SC.say("list size:" + result.size());
//                    }
//                });
//            }
//        });

        layout.draw();
    }
}

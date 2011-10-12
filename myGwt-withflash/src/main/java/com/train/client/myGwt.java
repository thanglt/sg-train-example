package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class myGwt implements EntryPoint {
 private final Messages msg = GWT.create(Messages.class);

  public void onModuleLoad() {

      IButton btn = new IButton("show window");

      btn.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent clickEvent) {
              new PicSettingWindow().show();
          }
      });

      VLayout lay = new VLayout();
      lay.addMember(new NewsPicPane());
      lay.addMember(btn);
      lay.setBackgroundColor("yellow");
      lay.setWidth100();
      lay.setHeight100();
      lay.draw();
  }
}

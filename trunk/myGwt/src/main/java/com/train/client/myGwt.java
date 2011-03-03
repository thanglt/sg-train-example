package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class myGwt implements EntryPoint {
 private final Messages msg = GWT.create(Messages.class);

  public void onModuleLoad() {

      //a DynamicForm
      DynamicForm form = new DynamicForm();

      //name input
      final TextItem textItem = new TextItem();
      textItem.setTitle(msg.nameField());

      //send button
      ButtonItem sendBtn = new ButtonItem(msg.sendButton());
      sendBtn.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
          public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent clickEvent) {
              if(textItem.getValue()!=null){
                  SC.say(msg.nameField()+ " : " + textItem.getValue().toString());
              }
          }
      });

      form.setItems(textItem , sendBtn );

      VLayout lay = new VLayout();
      lay.setWidth100();
      lay.setHeight100();
      lay.addMember(form);
      lay.draw();
  }
}

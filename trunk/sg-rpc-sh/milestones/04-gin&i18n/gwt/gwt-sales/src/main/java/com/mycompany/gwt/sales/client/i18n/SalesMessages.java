package com.mycompany.gwt.sales.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	E:/svn/sg-sh/trunk/gwt/gwt-sales/src/main/resources/com/mycompany/gwt/sales/client/i18n/SalesMessages.properties'.
 */
public interface SalesMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Welcome {0} back!".
   * 
   * @return translated "Welcome {0} back!"
   */
  @DefaultMessage("Welcome {0} back!")
  @Key("msg.welcome.back")
  String msg_welcome_back(String arg0);
}

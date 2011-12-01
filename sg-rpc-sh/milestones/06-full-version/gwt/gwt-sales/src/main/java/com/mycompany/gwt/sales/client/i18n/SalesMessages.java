package com.mycompany.gwt.sales.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	E:/svn/sg-sh/trunk/gwt/gwt-sales/src/main/resources/com/mycompany/gwt/sales/client/i18n/SalesMessages.properties'.
 */
public interface SalesMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "current module:{0}".
   * 
   * @return translated "current module:{0}"
   */
  @DefaultMessage("current module:{0}")
  @Key("modPath")
  String modPath(String arg0);

  /**
   * Translated "Welcome : {0}".
   * 
   * @return translated "Welcome : {0}"
   */
  @DefaultMessage("Welcome : {0}")
  @Key("msg.welcome")
  String msg_welcome(String arg0);

  /**
   * Translated "Welcome {0} back!".
   * 
   * @return translated "Welcome {0} back!"
   */
  @DefaultMessage("Welcome {0} back!")
  @Key("msg.welcome.back")
  String msg_welcome_back(String arg0);

  /**
   * Translated "send time:{0},from{1}".
   * 
   * @return translated "send time:{0},from{1}"
   */
  @DefaultMessage("send time:{0},from{1}")
  @Key("msgTime")
  String msgTime(String arg0,  String arg1);

  /**
   * Translated "Role : {0} is a system role, should not be delete!".
   * 
   * @return translated "Role : {0} is a system role, should not be delete!"
   */
  @DefaultMessage("Role : {0} is a system role, should not be delete!")
  @Key("warning.not.delete.isSystem")
  String warning_not_delete_isSystem(String arg0);
}

package com.train.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	F:/svn/openSource/sg-train-example/sg-gwtp/src/main/resources/com/train/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Application Start...".
   * 
   * @return translated "Application Start..."
   */
  @DefaultMessage("Application Start...")
  @Key("appStart")
  String appStart();

  /**
   * Translated "Application Menu init...".
   * 
   * @return translated "Application Menu init..."
   */
  @DefaultMessage("Application Menu init...")
  @Key("initAppMenu")
  String initAppMenu();

  /**
   * Translated "Masthead init...".
   * 
   * @return translated "Masthead init..."
   */
  @DefaultMessage("Masthead init...")
  @Key("initMasthead")
  String initMasthead();

  /**
   * Translated "Enter your name".
   * 
   * @return translated "Enter your name"
   */
  @DefaultMessage("Enter your name")
  @Key("nameField")
  String nameField();

  /**
   * Translated "Send".
   * 
   * @return translated "Send"
   */
  @DefaultMessage("Send")
  @Key("sendButton")
  String sendButton();
}

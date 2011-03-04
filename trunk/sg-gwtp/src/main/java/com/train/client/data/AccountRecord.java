package com.train.client.data;

import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountRecord extends ListGridRecord{
       public AccountRecord() {}

	  public AccountRecord(String icon,
	                       String accountName,
	                       String mainPhone,
	                       String location,
	                       String primaryContact,
	                       String emailPrimaryContact) {
	  setIcon(icon);
	  setAccountName(accountName);
	  setMainPhone(mainPhone);
	  setLocation(location);
	  setPrimaryContact(primaryContact);
	  setEmailPrimaryContact(emailPrimaryContact);
	  }

	  public void setIcon(String icon) {
	   setAttribute("icon", icon);
	  }

	  public void setAccountName(String accountName) {
	    setAttribute("accountName", accountName);
	  }

	  public void setMainPhone(String mainPhone) {
	    setAttribute("mainPhone", mainPhone);
	  }

	  public void setLocation(String location) {
	    setAttribute("location", location);
	  }

	  public void setPrimaryContact(String primaryContact) {
	    setAttribute("primaryContact", primaryContact);
	  }

	  public void setEmailPrimaryContact(String emailPrimaryContact) {
	    setAttribute("emailPrimaryContact", emailPrimaryContact);
	  }

	  public String getIcon() {
	    return getAttributeAsString("icon");
	  }

	  public String getAccountName() {
	    return getAttributeAsString("accountName");
	  }

	  public String getMainPhone() {
	    return getAttributeAsString("mainPhone");
	  }

	  public String getLocation() {
	    return getAttributeAsString("location");
	  }

	  public String getPrimaryContact() {
	    return getAttributeAsString("primaryContact");
	  }

	  public String getEmailPrimaryContact() {
	    return getAttributeAsString("emailPrimaryContact");
	  }
}

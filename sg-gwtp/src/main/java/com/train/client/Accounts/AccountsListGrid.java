package com.train.client.Accounts;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.train.client.data.AccountData;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 6:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountsListGrid extends ListGrid{

    public AccountsListGrid() {
        super();

        GWT.log("AccountsListGrid init...");
        this.setShowAllRecords(true);
        this.setSortField(1);

        ListGridField iconField = new ListGridField("icon", "#", 27);
	    iconField.setImageSize(16);
	    iconField.setAlign(Alignment.CENTER);
	    iconField.setType(ListGridFieldType.IMAGE);
	    iconField.setImageURLPrefix("icons/16/");
	    iconField.setImageURLSuffix(".png");

        ListGridField accountNameField = new ListGridField("accountName", "Account Name", 320);
	    ListGridField mainPhoneField = new ListGridField("mainPhone", "Main Phone", 100);
	    ListGridField locationField = new ListGridField("location", "Location", 100);
	    ListGridField primaryContactField = new ListGridField("primaryContact", "Primary Contact", 140);
	    primaryContactField.setType(ListGridFieldType.LINK);
	    ListGridField emailPrimaryContactField = new ListGridField("emailPrimaryContact",
	                                                               "Email (Primary Contact)", 180);
	    ListGridField emptyField = new ListGridField("emptyField", " ");

        this.setFields(new ListGridField[] {iconField, accountNameField, mainPhoneField, locationField,
	                   primaryContactField, emailPrimaryContactField, emptyField });

	    this.setData(AccountData.getRecords());

    }
}

package com.mycompany.gwt.sales.client.view.systemManage.assignRole2User;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.mycompany.gwt.sales.client.datasource.RoleDataSource;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/19/11
 * Description: put description for the type here...
 */
public class SimpleRoleListGrid extends ListGrid {

    public SimpleRoleListGrid() {
        SalesConstants constants = GWT.create(SalesConstants.class);  //i18n constant

        setWidth(300);
        setHeight(200);
        setHeaderHeight(40);

        ListGridField idField = new ListGridField("id");
        idField.setHidden(true);

        ListGridField nameField = new ListGridField("name" , constants.ds_role_name());

        ListGridField descriptionField = new ListGridField("description" , constants.ds_role_description());

        setFields(idField, nameField, descriptionField);
    }

    public void setHeaderSpanName(String spanName){
        HeaderSpan headerSpan = new HeaderSpan(spanName, new String[]{"name", "value" , "isSystem" , "description"});
        this.setHeaderSpans(headerSpan);
    }

}

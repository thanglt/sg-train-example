package com.mycompany.gwt.sales.client.view.main;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tab.TabSet;

/*
* 中部面板
*/
@Singleton
public class MainLayout extends HLayout {

    @Inject
    public MainLayout(SideNavSectionStack sideNavSectionStack , MainCanvas mainTabSet) {
        setWidth100();
        setHeight100();

        addMember(sideNavSectionStack);
		addMember(mainTabSet);
    }
}

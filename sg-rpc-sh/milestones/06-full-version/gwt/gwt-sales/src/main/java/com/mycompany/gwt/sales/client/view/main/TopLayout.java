package com.mycompany.gwt.sales.client.view.main;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * 顶部面板
 */
@Singleton
public class TopLayout extends HLayout {

    @Inject
    public TopLayout(LogoImg logoImg, TopToolStrip topToolStrip , LogoutToolStrip logoutToolStrip) {

        setWidth100();
        setHeight(30);
        setBackgroundColor("yellow");

        addMember(logoImg);  //logo 图片
        addMember(topToolStrip); //模块菜单组
        addMember(logoutToolStrip); //登出菜单组
    }
}

package com.mycompany.gwt.sales.client.view.main;

import com.google.inject.Inject;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: 左侧的 Accordion, 用来放置 SideNavTree
 */
public class SideNavSectionStack extends SectionStack {

    @Inject
    public SideNavSectionStack(SideNavTree sideNavTree , SalesConstants constants) {
        setWidth(200);
        setShowResizeBar(true);// 可调节
        setVisibilityMode(VisibilityMode.MUTEX);// 设置可见性
        setAnimateSections(true);// 有动画

        // 导航树面版
        SectionStackSection sideNavTreeSection = new SectionStackSection(constants.nav_tree_header());
//        sideNavTreeSection.setShowHeader(false);   //显示头部
        sideNavTreeSection.setExpanded(true);// 设置展开
        sideNavTreeSection.setItems(sideNavTree);
        sideNavTreeSection.setResizeable(true);

        setSections(sideNavTreeSection);
    }
}

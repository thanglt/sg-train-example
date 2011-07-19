/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.client;

import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.sample.showcase.client.data.ExplorerTreeNode;
import com.smartgwt.sample.showcase.client.data.ShowcaseData;


public class SideNavTree extends TreeGrid {
    private String idSuffix = "";

    private ExplorerTreeNode[] showcaseData = ShowcaseData.getData(idSuffix);

    public SideNavTree() {
        setWidth100();
        setHeight100();
        setCustomIconProperty("icon");
        setAnimateFolderTime(100);
        setAnimateFolders(true);
        setAnimateFolderSpeed(1000);        
        setShowSortArrow(SortArrow.CORNER);
        setShowAllRecords(true);
        setLoadDataOnDemand(false);
        setCanSort(false);
        setLeaveScrollbarGap(false);

        TreeGridField field = new TreeGridField();
        field.setCanFilter(true);
        field.setName("name");
        field.setTitle("<b>SmartGWT EE Showcase</b>");
        setFields(field);

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setNameProperty("name");
        tree.setOpenProperty("isOpen");
        tree.setIdField("nodeID");
        tree.setParentIdField("parentNodeID");
        tree.setRootValue("root" + idSuffix);

        tree.setData(showcaseData);

        setData(tree);
    }

    public ExplorerTreeNode[] getShowcaseData() {
        return showcaseData;
    }
}

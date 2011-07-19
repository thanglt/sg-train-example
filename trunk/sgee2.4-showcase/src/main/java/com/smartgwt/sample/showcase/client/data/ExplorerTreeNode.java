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

package com.smartgwt.sample.showcase.client.data;

import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.PanelFactory;


public class ExplorerTreeNode extends TreeNode {

    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, PanelFactory factory, boolean enabled, String idSuffix) {
        if (enabled) {
            setName(name);
        } else {
            setName("<span style='color:808080'>" + name + "</span>");
        }
        setNodeID(nodeID.replace("-", "_") + idSuffix);
        setParentNodeID(parentNodeID.replace("-", "_") + idSuffix);
        setIcon(icon);
        setFactory(factory);
        if(nodeID.equals("new-category")) {
            setIsOpen(false);
        } else {
            setIsOpen(true);
        }
    }

    public void setFactory(PanelFactory factory) {
        setAttribute("factory", factory);
    }

    public PanelFactory getFactory() {
        return (PanelFactory) getAttributeAsObject("factory");
    }

    public void setNodeID(String value) {
        setAttribute("nodeID", value);
    }

    public String getNodeID() {
        return getAttribute("nodeID");
    }

    public void setParentNodeID(String value) {
        setAttribute("parentNodeID", value);
    }

    public void setName(String name) {
        setAttribute("name", name);
    }

    public String getName() {
        return getAttributeAsString("name");
    }

    public void setIcon(String icon) {
        setAttribute("icon", icon);
    }

    public String getIcon() {
        return getAttributeAsString("icon");
    }

    public void setIsOpen(boolean isOpen) {
        setAttribute("isOpen", isOpen);
    }

    public void setIconSrc(String iconSrc) {
        setAttribute("iconSrc", iconSrc);
    }

    public String getIconSrc() {
        return getAttributeAsString("iconSrc");
    }

}

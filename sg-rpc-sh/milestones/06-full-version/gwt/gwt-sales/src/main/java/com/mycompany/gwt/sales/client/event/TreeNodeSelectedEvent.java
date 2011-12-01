package com.mycompany.gwt.sales.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
public class TreeNodeSelectedEvent extends GwtEvent<TreeNodeSelectedEventHandler>{

    public static Type<TreeNodeSelectedEventHandler> TYPE = new Type<TreeNodeSelectedEventHandler>();

    public String nodeName;
    public String viewId;
    public String icon;

    public TreeNodeSelectedEvent(String nodeName , String viewId , String icon) {
        this.nodeName = nodeName;
        this.viewId = viewId;
        this.icon = icon;
    }

    @Override
    public Type<TreeNodeSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(TreeNodeSelectedEventHandler handler) {
        handler.onEvent(this);
    }
}

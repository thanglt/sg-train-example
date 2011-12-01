package com.mycompany.gwt.sales.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/12/11
 * Description: Module Selected Event
 */
public class ModuleSelectedEvent extends GwtEvent<ModuleSelectedEventHandler>{

    public static Type<ModuleSelectedEventHandler> TYPE = new Type<ModuleSelectedEventHandler>();

    public String moduleName;

    public ModuleSelectedEvent(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public Type<ModuleSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModuleSelectedEventHandler handler) {
        handler.onEvent(this);
    }
}

// WARNING: THIS FILE IS MANAGED BY SPRING ROO.

package com.mycompany.roo.client.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;
import org.springframework.roo.addon.gwt.RooGwtProxy;

@ProxyForName(value = "com.mycompany.roo.domain.Student", locator = "com.mycompany.roo.server.locator.StudentLocator")
@RooGwtProxy(value = "com.mycompany.roo.domain.Student", readOnly = { "version", "id" }, scaffold = true)
public interface StudentProxy extends EntityProxy {

    abstract Long getId();

    abstract String getName();

    abstract void setName(String name);

    abstract Integer getVersion();
}

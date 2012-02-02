// WARNING: THIS FILE IS MANAGED BY SPRING ROO.

package com.mycompany.roo.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;
import com.mycompany.roo.client.proxy.StudentProxy;
import org.springframework.roo.addon.gwt.RooGwtRequest;

@RooGwtRequest("com.mycompany.roo.domain.Student")
@ServiceName(value = "com.mycompany.roo.service.StudentService", locator = "com.mycompany.roo.server.locator.GwtServiceLocator")
public interface StudentRequest extends RequestContext {

    abstract Request<java.lang.Long> countAllStudents();

    abstract Request<java.util.List<com.mycompany.roo.client.proxy.StudentProxy>> findAllStudents();

    abstract Request<java.util.List<com.mycompany.roo.client.proxy.StudentProxy>> findStudentEntries(int firstResult, int maxResults);

    abstract Request<com.mycompany.roo.client.proxy.StudentProxy> findStudent(Long id);

    abstract Request<java.lang.Void> saveStudent(StudentProxy proxy);

    abstract Request<java.lang.Void> deleteStudent(StudentProxy proxy);
}

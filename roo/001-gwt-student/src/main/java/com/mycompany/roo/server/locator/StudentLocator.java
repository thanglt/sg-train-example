package com.mycompany.roo.server.locator;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.mycompany.roo.domain.Student;
import com.mycompany.roo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.gwt.RooGwtLocator;
import org.springframework.stereotype.Component;

@RooGwtLocator("com.mycompany.roo.domain.Student")
@Component
public class StudentLocator extends Locator<Student, Long> {

    @Autowired
    StudentService studentService;

    public Student create(Class<? extends com.mycompany.roo.domain.Student> clazz) {
        return new Student();
    }

    public Student find(Class<? extends com.mycompany.roo.domain.Student> clazz, Long id) {
        return studentService.findStudent(id);
    }

    public Class<com.mycompany.roo.domain.Student> getDomainType() {
        return Student.class;
    }

    public Long getId(Student student) {
        return student.getId();
    }

    public Class<java.lang.Long> getIdType() {
        return Long.class;
    }

    public Object getVersion(Student student) {
        return student.getVersion();
    }
}

package com.spring.datasource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-11-17
 * Time: 上午1:40
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "classroomOutput")
public class ClassroomOutputType {

    private ArrayList<Classroom> classes;

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public ClassroomOutputType() {
    }

    public ClassroomOutputType(ArrayList<Classroom> list) {
        this.classes = list;
        this.test = new Date().toString();
    }

    @Override
    public String toString() {
        return "ClassroomOutputType{" +
                "classes=" + classes +
                '}';
    }

    @XmlElement(name="classroom")//this name is for each element in ArrayList.
    public ArrayList<Classroom> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Classroom> classes) {
        this.classes = classes;
    }
}

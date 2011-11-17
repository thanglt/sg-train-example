package com.roo.pizzashop.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@RooJavaBean
@RooToString
@RooEntity
@XmlType(propOrder = {"id" , "name" , "remark" , "hotIndex" , "version"})
public class Base {

    @NotNull
    @Size(min = 2)
    private String name;

    private String remark;

    private String hotIndex;
}

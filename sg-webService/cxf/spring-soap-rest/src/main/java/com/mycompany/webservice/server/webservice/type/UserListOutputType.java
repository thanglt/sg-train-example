package com.mycompany.webservice.server.webservice.type;


import com.mycompany.webservice.server.entity.User;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class UserListOutputType {

    @XmlElement(name = "User")
    private List<User> user;

    public List<User> getUser() {
        if (user == null) {
            user = new ArrayList<User>();
        }
        return this.user;
    }

}

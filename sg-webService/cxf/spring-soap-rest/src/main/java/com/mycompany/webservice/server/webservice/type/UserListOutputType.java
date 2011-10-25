package com.mycompany.webservice.server.webservice.type;


import com.mycompany.webservice.server.entity.User;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = { "user" })
public class UserListOutputType {

    @XmlElement
    private List<User> user;

    public List<User> getUser() {
        if (user == null) {
            user = new ArrayList<User>();
        }
        return this.user;
    }

}

package com.mycompany.webservice.sample.server.webservice.type;

import com.mycompany.webservice.sample.server.entity.User;

import javax.jws.WebResult;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

//element type & complexType name
@XmlType(name = "UserListOutputType", propOrder = {
    "user"
})
public class UserListOutputType {

    //element name
    @XmlElement(name = "User")
    private List<User> user;

    public List<User> getUser() {
        if (user == null) {
            user = new ArrayList<User>();
        }
        return this.user;
    }

}

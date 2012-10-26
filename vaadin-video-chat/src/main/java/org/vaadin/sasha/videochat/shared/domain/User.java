package org.vaadin.sasha.videochat.shared.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    
    private String userName;
    
    private String password;
    
    private List<User> contactList = new LinkedList<User>();
    
    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }
    
    public List<User> getContactList() {
        return contactList;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

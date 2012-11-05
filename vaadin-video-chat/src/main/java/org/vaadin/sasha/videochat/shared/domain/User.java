package org.vaadin.sasha.videochat.shared.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email;

    private String userName;

    private String test;
    
    private List<User> contactList = new LinkedList<User>();

    public String getTest() {
        return test;
    }
    
    public void setTest(String test) {
        this.test = test;
    }
    
    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    @OneToMany
    public List<User> getContactList() {
        return contactList;
    }

    public String getUserName() {
        return userName;
    }

    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

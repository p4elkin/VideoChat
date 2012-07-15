package org.vaadin.sasha.videochat.server;

import java.io.Serializable;

public class User implements Serializable {
    
    private int userId;
    
    private String userName;
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }
}

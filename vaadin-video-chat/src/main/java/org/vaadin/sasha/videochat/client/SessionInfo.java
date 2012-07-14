package org.vaadin.sasha.videochat.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SessionInfo implements Serializable {
    
    private int userId;
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getUserId() {
        return userId;
    }
}

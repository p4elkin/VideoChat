package org.vaadin.sasha.videochat.server.event;

public class UserOnlineStatusMessage extends ContactListMessage {

    private int userId;
    
    private boolean isOnline;

    public UserOnlineStatusMessage(int userId, boolean isOnline) {
        setContactMessageType("ONLINE_STATUS");
        this.userId = userId;
        this.isOnline = isOnline;
    }
    
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

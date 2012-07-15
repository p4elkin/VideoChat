package org.vaadin.sasha.videochat.server.event;

import java.io.Serializable;

public class Message implements Serializable {

    private String messageType;
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}

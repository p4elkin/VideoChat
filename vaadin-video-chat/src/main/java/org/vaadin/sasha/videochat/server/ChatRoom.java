package org.vaadin.sasha.videochat.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatRoom implements Serializable {
    
    private final int id;
    
    private final int firstParticipantId;
    
    private int secondParticipantId;
    
    public ChatRoom(int id, int initiatorId) {
        this.id = id;
        this.firstParticipantId = id;
    }
    
    public int getFirstParticipantId() {
        return firstParticipantId;
    }
    
    public int getSecondParticipantId() {
        return secondParticipantId;
    }
    
    public void setSecondParticipantId(int secondParticipantId) {
        this.secondParticipantId = secondParticipantId;
    }
    
    public int getId() {
        return id;
    }

}

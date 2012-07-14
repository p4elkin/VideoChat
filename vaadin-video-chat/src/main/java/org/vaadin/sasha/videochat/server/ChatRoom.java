package org.vaadin.sasha.videochat.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatRoom implements Serializable {
    
    public enum RoomState {
        
    };
    
    private final int id;
    
    private final int firstParticipantId;
    
    private int secondParticipantId;
    
    private RoomState currentState;
    
    public ChatRoom(int id, int initiatorId) {
        this.id = id;
        this.firstParticipantId = id;
    }
    
    public void setCurrentState(RoomState currentState) {
        this.currentState = currentState;
    }
    
    public RoomState getCurrentState() {
        return currentState;
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

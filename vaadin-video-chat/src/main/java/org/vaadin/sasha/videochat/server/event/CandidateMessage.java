package org.vaadin.sasha.videochat.server.event;

public class CandidateMessage extends Message {
    
    private String type;
    
    private String candidate;
    
    private String label;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    

}

package org.vaadin.sasha.videochat.server.event;


@SuppressWarnings("serial")
public class NegotiationMessage extends Message {
    
    private String type;
    
    private String sdp;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    
    public String getSdp() {
        return sdp;
    }
    
    public void setSdp(String sdp) {
        this.sdp = sdp;
    }
    
}

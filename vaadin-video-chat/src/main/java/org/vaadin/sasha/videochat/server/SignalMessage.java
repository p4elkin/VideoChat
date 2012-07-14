package org.vaadin.sasha.videochat.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SignalMessage implements Serializable {
    
    private String messageType;
    
    private String offererSessionId;
    
    private String sdp;
    
    private long seq;
    
    private long tieBreaker;
    
    public String getMessageType() {
        return messageType;
    }
    
    public String getOffererSessionId() {
        return offererSessionId;
    }
    
    public String getSdp() {
        return sdp;
    }
    
    public long getSeq() {
        return seq;
    }
    
    public long getTieBreaker() {
        return tieBreaker;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public void setOffererSessionId(String offererSessionId) {
        this.offererSessionId = offererSessionId;
    }
    
    public void setSdp(String sdp) {
        this.sdp = sdp;
    }
    
    public void setSeq(long seq) {
        this.seq = seq;
    }
    
    public void setTieBreaker(long tieBreaker) {
        this.tieBreaker = tieBreaker;
    }
    
}

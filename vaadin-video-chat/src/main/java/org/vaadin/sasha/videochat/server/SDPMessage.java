package org.vaadin.sasha.videochat.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SDPMessage implements Serializable {

    private SignalMessage signalMessage;
    
    public SignalMessage getSignalMessage() {
        return signalMessage;
    }
    
    public void setSignalMessage(SignalMessage signalMessage) {
        this.signalMessage = signalMessage;
    }
    
    
}

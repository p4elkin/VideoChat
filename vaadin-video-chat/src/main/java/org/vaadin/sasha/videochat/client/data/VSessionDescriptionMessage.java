package org.vaadin.sasha.videochat.client.data;


public abstract class VSessionDescriptionMessage extends VMessage {

    protected VSessionDescriptionMessage() {}
    
    public final native String getSDPType() /*-{
        return this.type;
    }-*/;
    
}

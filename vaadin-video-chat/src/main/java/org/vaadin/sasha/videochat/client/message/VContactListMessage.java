package org.vaadin.sasha.videochat.client.message;

public class VContactListMessage extends VMessage {

    protected VContactListMessage() {}
    
    public final native String getContactMessageType() /*-{
        return this.contactMessageType;
    }-*/; 
}

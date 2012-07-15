package org.vaadin.sasha.videochat.client.data;

public class VContactListMessage extends VMessage {

    public final native String getContactMessageType() /*-{
        return this.contactMessageType;
    }-*/; 
}

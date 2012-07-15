package org.vaadin.sasha.videochat.server.event;

public class ContactListMessage extends Message {

    private String contactMessageType;

    public ContactListMessage() {
        setMessageType("CONTACTS");
    }
    
    public String getContactMessageType() {
        return contactMessageType;
    }

    public void setContactMessageType(String contactMessageType) {
        this.contactMessageType = contactMessageType;
    }
}

package org.vaadin.sasha.videochat.client.message;

public class VUserOnlineStatusMessage extends VContactListMessage {

    protected VUserOnlineStatusMessage() {}
    
    public native final boolean isOnline() /*-{
		return this.isOnline;
    }-*/;

    public native final int getUserId() /*-{
		return this.userId;
    }-*/;
}

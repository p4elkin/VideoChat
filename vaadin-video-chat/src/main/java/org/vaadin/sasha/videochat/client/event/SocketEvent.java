package org.vaadin.sasha.videochat.client.event;

import org.vaadin.sasha.videochat.client.data.VMessage;

import com.google.web.bindery.event.shared.Event;

public class SocketEvent extends Event<SocketEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public VMessage jsonMessage;
    
    public SocketEvent(final VMessage message) {
        super();
        this.jsonMessage = message;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        final String type = jsonMessage.getMessageType();
        if ("NEGOTIATION".equals(type)) {
           handler.onSessionDescriptionMessage(this);
        } else if ("CONTACTS".equals(type)) {
            handler.onContactListMessage(this);
        }
    }
    
    public VMessage getJson() {
        return jsonMessage;
    }
    
    public interface Handler {
        
        void onSessionDescriptionMessage(final SocketEvent event);
        
        void onContactListMessage(final SocketEvent event);
    }
    
    public static class SocketHandlerAdapter implements Handler {

        @Override
        public void onSessionDescriptionMessage(SocketEvent event) {}

        @Override
        public void onContactListMessage(SocketEvent event) {}
        
    }

}

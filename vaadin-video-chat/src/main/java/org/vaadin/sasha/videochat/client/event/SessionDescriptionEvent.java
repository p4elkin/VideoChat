package org.vaadin.sasha.videochat.client.event;

import org.vaadin.sasha.videochat.client.data.VSessionDescriptionMessage;

import com.google.web.bindery.event.shared.Event;

public class SessionDescriptionEvent extends Event<SessionDescriptionEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public VSessionDescriptionMessage message;
    
    public SessionDescriptionEvent(VSessionDescriptionMessage message) {
        super();
        this.message = message;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onSessionDescriptionEvent(this);
    }
    
    public VSessionDescriptionMessage getMessage() {
        return message;
    }
    
    public interface Handler {
        void onSessionDescriptionEvent(final SessionDescriptionEvent event);
    }

}

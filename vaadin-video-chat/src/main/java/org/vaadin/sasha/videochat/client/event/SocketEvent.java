package org.vaadin.sasha.videochat.client.event;

import com.google.web.bindery.event.shared.Event;

public class SocketEvent extends Event<SocketEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public String jsonMessage;
    
    public SocketEvent(final String jsonMessage) {
        super();
        this.jsonMessage = jsonMessage;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onSocketMessage(this);
    }
    
    public String getJson() {
        return jsonMessage;
    }
    
    public interface Handler {
        void onSocketMessage(final SocketEvent event);
    }

}

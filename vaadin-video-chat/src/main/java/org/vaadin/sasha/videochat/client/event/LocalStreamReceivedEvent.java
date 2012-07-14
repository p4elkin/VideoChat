package org.vaadin.sasha.videochat.client.event;

import com.google.web.bindery.event.shared.Event;

import elemental.dom.LocalMediaStream;


public class LocalStreamReceivedEvent extends Event<LocalStreamReceivedEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public LocalMediaStream stream;
    
    public LocalStreamReceivedEvent(final LocalMediaStream stream) {
        super();
        this.stream = stream;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onStreamReceived(this);
    }
    
    public LocalMediaStream getStream() {
        return stream;
    }
    
    public interface Handler {
        void onStreamReceived(final LocalStreamReceivedEvent event);
    }

}
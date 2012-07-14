package org.vaadin.sasha.videochat.client.event;

import com.google.web.bindery.event.shared.Event;

import elemental.dom.MediaStream;

public class RemoteStreamReceivedEvent extends Event<RemoteStreamReceivedEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public MediaStream stream;
    
    public RemoteStreamReceivedEvent(final MediaStream stream) {
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
    
    public MediaStream getStream() {
        return stream;
    }
    
    public interface Handler {
        void onStreamReceived(final RemoteStreamReceivedEvent event);
    }

}

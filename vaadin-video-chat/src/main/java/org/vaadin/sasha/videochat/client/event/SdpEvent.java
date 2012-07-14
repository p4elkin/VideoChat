package org.vaadin.sasha.videochat.client.event;

import org.vaadin.sasha.videochat.client.data.VSDPMessage;

import com.google.web.bindery.event.shared.Event;

public class SdpEvent extends Event<SdpEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public VSDPMessage message;
    
    public SdpEvent(VSDPMessage message) {
        super();
        this.message = message;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onCandidate(this);
    }
    
    public VSDPMessage getMessage() {
        return message;
    }
    
    public interface Handler {
        void onCandidate(final SdpEvent event);
    }

}

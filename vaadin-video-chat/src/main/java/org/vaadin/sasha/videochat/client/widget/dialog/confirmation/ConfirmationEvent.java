package org.vaadin.sasha.videochat.client.widget.dialog.confirmation;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ConfirmationEvent extends GwtEvent<ConfirmationEvent.Handler>{

    interface Handler extends EventHandler {
        void onConfirmation(ConfirmationEvent event);
    }
    
    public static Type<Handler> TYPE = new Type<ConfirmationEvent.Handler>();
    
    boolean isConfirmed;
    
    public ConfirmationEvent(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    
    @Override
    protected void dispatch(Handler handler) {
        handler.onConfirmation(this);
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }
}

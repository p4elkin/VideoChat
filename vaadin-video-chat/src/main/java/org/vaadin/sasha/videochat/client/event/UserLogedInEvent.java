package org.vaadin.sasha.videochat.client.event;

import com.google.web.bindery.event.shared.Event;

public class UserLogedInEvent extends Event<UserLogedInEvent.Handler>{

    public final static Type<Handler> TYPE = new Type<Handler>();
    
    public int userId;
    
    public UserLogedInEvent(int userId) {
        super();
        this.userId = userId;
    }
    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onUserLogedIn(this);
    }
    
    public int getUserId() {
        return userId;
    }
    
    public interface Handler {
        void onUserLogedIn(final UserLogedInEvent event);
    }

}

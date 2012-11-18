package org.vaadin.sasha.videochat.client.ui.contacts;

import java.util.List;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {
    
    void setContacts(List<User> result);
    
    void userOnlineStatusChanged(int userId, boolean online);
    
    interface Presenter {
        
        void setView(ContactsView view);
        
        void loadContacts();
        
    }
}

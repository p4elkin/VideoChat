package org.vaadin.sasha.videochat.client.ui.contacts;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {
    
    void setContacts(List<String> result);
    
    void userOnlineStatusChanged(int userId, boolean online);
    
    interface Presenter {
        
        void setView(ContactsView view);
        
        void loadContacts();
        
    }
}

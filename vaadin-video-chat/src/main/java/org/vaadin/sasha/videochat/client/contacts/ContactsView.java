package org.vaadin.sasha.videochat.client.contacts;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {
    
    void setContacts(List<String> result);
    
    interface Presenter {
        
        void loadContacts();
        
    }
}

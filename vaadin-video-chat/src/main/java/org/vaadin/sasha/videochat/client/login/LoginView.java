package org.vaadin.sasha.videochat.client.login;

import com.google.gwt.user.client.ui.IsWidget;

public interface LoginView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    interface Presenter {
        
        void login(String userName, String password);
        
    }
}

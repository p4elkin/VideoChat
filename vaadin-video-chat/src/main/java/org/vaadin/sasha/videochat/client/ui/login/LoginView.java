package org.vaadin.sasha.videochat.client.ui.login;

import com.google.gwt.user.client.ui.IsWidget;

public interface LoginView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    interface Presenter {
        
        void signIn();
        
        void setUserName(String userName);
        
        void setEmail(String password);

        void register();
        
    }
}

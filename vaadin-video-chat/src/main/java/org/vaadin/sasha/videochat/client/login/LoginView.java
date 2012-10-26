package org.vaadin.sasha.videochat.client.login;

import com.google.gwt.user.client.ui.IsWidget;

public interface LoginView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    interface Presenter {
        
        void signIn();
        
        void setUserName(String userName);
        
        void setPassword(String password);
        
        void setDuplicatePassword(String password);
        
        void setEmail(String password);

        void register();
        
    }
}

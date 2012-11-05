package org.vaadin.sasha.videochat.client.login;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.SessionInfo;
import org.vaadin.sasha.videochat.client.VideoChatServiceAsync;
import org.vaadin.sasha.videochat.client.chat.VideoChatPlace;
import org.vaadin.sasha.videochat.client.event.UserLogedInEvent;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class LoginActivity extends AbstractActivity implements LoginView.Presenter {

    private final VideoChatServiceAsync service;
    
    private final LoginView view;
    
    private boolean isAuthenticated = false;

    private SessionInfo sessionInfo;
    
    private PlaceController controller;
    
    private EventBus eventBus;
    
    private String password;
    
    private String userName;
    
    private String email;
    
    private String duplicatePassword;
    
    private User user;
    
    @Inject
    public LoginActivity(final LoginView view, VideoChatServiceAsync service, 
            PlaceController controller, SessionInfo sessionInfo, EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.controller = controller;
        this.sessionInfo = sessionInfo;
        this.service = service;
        this.view = view;
        view.setPresenter(this);
    }
    
    @Override
    public String mayStop() {
        final String msg = isAuthenticated ? super.mayStop() : "Please authenticate";
        return msg;
    }
    
    @Override
    public void start(AcceptsOneWidget viewport, com.google.gwt.event.shared.EventBus eventBus) {
        viewport.setWidget(view.asWidget());
    }

    @Override
    public void signIn() {
        final User user = new User();
        //user.setPassword(password);
        user.setUserName(userName);
        service.signIn(user, new AsyncCallback<Integer>() {
            
            @Override
            public void onSuccess(Integer userId) {
                isAuthenticated = true;
                sessionInfo.setUserId(userId);
                controller.goTo(new VideoChatPlace());
                eventBus.fireEvent(new UserLogedInEvent(userId));
            }
            
            @Override
            public void onFailure(Throwable caught) {
                
            }
        });
    }
   
    @Override
    public void register() {
        final User user = new User();
        //user.setPassword(password);
        user.setUserName(userName);
        service.register(user, new AsyncCallback<Integer>() {
            
            @Override
            public void onSuccess(Integer userId) {
                isAuthenticated = true;
                sessionInfo.setUserId(userId);
                controller.goTo(new VideoChatPlace());
                eventBus.fireEvent(new UserLogedInEvent(userId));
            }
            
            @Override
            public void onFailure(Throwable caught) {
                
            }
        });
    }
    
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setDuplicatePassword(String password) {
        this.duplicatePassword = password;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
}

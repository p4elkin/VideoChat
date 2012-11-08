package org.vaadin.sasha.videochat.server;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.vaadin.sasha.videochat.client.VideoChatService;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.common.collect.Iterables;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Provider;
import com.google.inject.Singleton;


/**
 * The server side implementation of the RPC service.
 */
@Singleton
public class VideoChatServiceImpl extends RemoteServiceServlet implements VideoChatService {
    
    @Inject
    private Provider<HttpSession> sessionProvider;

    @Inject 
    private Provider<UserService> userServiceProvider;
    
    @Inject
    private Provider<User> currentUserProvider;
    
    public int createChatRoom(int creatorId) {
        return VideoChatRoomManager.createRoom(creatorId).getId();
    }

    public int signIn(User user) throws IllegalArgumentException {
        final UserService userService = userServiceProvider.get(); 
        User foundUser = userService.authenticate(user.getEmail());
        sessionProvider.get().setAttribute("user", foundUser);
        return userServiceProvider.get().getCurrentUserId();
    }

    @Override
    public List<String> getUsersOnline() {
        return VideoChatRoomManager.getUsersOnline();
    }

    @Override
    public int authenticate() throws IllegalArgumentException {
        User user = currentUserProvider.get(); 
        if (user != null) {
            return userServiceProvider.get().getCurrentUserId();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int register(User newUser) {
        User user = userServiceProvider.get().registerUser(newUser);
        sessionProvider.get().setAttribute("user", user);
        return userServiceProvider.get().getCurrentUserId();
    }
    
}

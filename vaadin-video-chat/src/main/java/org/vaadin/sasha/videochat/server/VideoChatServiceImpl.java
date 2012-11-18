package org.vaadin.sasha.videochat.server;

import java.util.List;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.VideoChatService;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Provider;
import com.google.inject.Singleton;


/**
 * The server side implementation of the RPC service.
 */
@Singleton
public class VideoChatServiceImpl extends RemoteServiceServlet implements VideoChatService {
    
    @Inject
    private Provider<UserService> userServiceProvider;

    @Inject
    private Provider<SessionCtx> sessionCtxProvider;
    
    @Override
    public int createChatRoom(int creatorId) {
        return VideoChatRoomManager.createRoom(creatorId).getId();
    }

    @Override
    public int signIn(User user) throws IllegalArgumentException {
        final User foundUser = userServiceProvider.get().signIn(user.getEmail());
        if (foundUser != null) {
            return setUserOnline(foundUser);
        } else {
            throw new IllegalArgumentException("User not found");
        }
        
    }

    private Integer setUserOnline(User foundUser) {
        sessionCtxProvider.get().setUser(foundUser);
        userServiceProvider.get().setCurrentUserOnline(true);
        return foundUser.getId();
    }

    @Override
    public List<User> getUsersOnline() {
        final User user = sessionCtxProvider.get().getUser(); 
        return user == null ? Lists.<User>newArrayList() : user.getContactList(); 
    }

    @Override
    public int authenticate() throws IllegalArgumentException {
        User user = sessionCtxProvider.get().getUser();
        if (user != null) {
            return user.getId();
        } else {
            throw new IllegalArgumentException("Not authenticated");   
        }
    }

    @Override
    public int register(User newUser) {
        final User user = userServiceProvider.get().registerUser(newUser);
        if (user != null) {
            return setUserOnline(user);
        }
        throw new IllegalArgumentException("Couldn't register new user");
    }

}

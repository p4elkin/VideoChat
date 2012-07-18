package org.vaadin.sasha.videochat.server;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.vaadin.sasha.videochat.client.VideoChatService;
import org.vaadin.sasha.videochat.server.service.user.UserService;

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
    
    public int createChatRoom(int creatorId) {
        return VideoChatRoomManager.createRoom(creatorId).getId();
    }

    public int login(String userName, String password) throws IllegalArgumentException {
        User user = userServiceProvider.get().authenticate(userName, password);
        sessionProvider.get().setAttribute("userId", user.getUserId());
        return user.getUserId();
    }

    @Override
    public List<String> getUsersOnline() {
        return VideoChatRoomManager.getUsersOnline();
    }
    
}

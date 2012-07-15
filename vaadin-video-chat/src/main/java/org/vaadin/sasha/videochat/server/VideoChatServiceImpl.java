package org.vaadin.sasha.videochat.server;

import java.util.List;

import org.vaadin.sasha.videochat.client.VideoChatService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class VideoChatServiceImpl extends RemoteServiceServlet implements VideoChatService {

    public int createChatRoom(int creatorId) {
        return VideoChatRoomManager.createRoom(creatorId).getId();
    }

    public int login(String userName, String password) throws IllegalArgumentException {
        User user = VideoChatRoomManager.getUser(userName);
        return user.getUserId();
    }

    @Override
    public List<String> getUsersOnline() {
        return VideoChatRoomManager.getUsersOnline();
    }
    
}

package org.vaadin.sasha.videochat.server;

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

    public int registerUser(final String userName) throws IllegalArgumentException {
        return VideoChatRoomManager.getUserId(userName);
    }
}

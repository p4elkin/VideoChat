package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.event.UserOnlineStatusMessage;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gson.Gson;

public class VideoChatSocket implements WebSocket.OnTextMessage {

    private VideoChatSocketManager socketManager;

    private UserService userService;

    private Connection connection;

    private final User user;
    
    @Inject
    public VideoChatSocket(VideoChatSocketManager socketManager, UserService service, User user) {
        super();
        this.user = user;
        this.userService = service;
        this.socketManager = socketManager;
    }

    @Override
    public void onClose(int closeCode, String message) {
        socketManager.deregisterSocket(this);
        final String statusMessage = new Gson().toJson(new UserOnlineStatusMessage(userService.getCurrentUserId(), false));
        broadcastMessageToContacts(statusMessage);
    }

    private void broadcastMessageToContacts(final String statusMessage) {
        final Iterable<User> contacts = userService.getContactsList();
        for (final User contact : contacts) {
            System.out.println("SENDING from " + user.getUserName() + " to " + contact.getUserName() + " " + statusMessage);
            final List<VideoChatSocket> sockets = socketManager.getSocketForUser(contact);
            if (sockets != null) {
                for (final VideoChatSocket socket : sockets) {
                    try {
                        socket.connection.sendMessage(statusMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        socketManager.registerSocket(this);
        System.out.println("[USER ID]: " + userService.getCurrentUserId());
        final String message = new Gson().toJson(new UserOnlineStatusMessage(userService.getCurrentUserId(), true));
        broadcastMessageToContacts(message);
    }

    @Override
    public void onMessage(final String jsonMessage) {
        System.out.println(jsonMessage);
        broadcastMessageToContacts(jsonMessage);
    }

    public User getUser() {
        return user;
    }
}
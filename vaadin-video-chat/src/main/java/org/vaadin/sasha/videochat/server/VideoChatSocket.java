package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.event.UserOnlineStatusMessage;
import org.vaadin.sasha.videochat.server.service.user.UserService;

import com.google.gson.Gson;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class VideoChatSocket implements WebSocket.OnTextMessage {

    private Map<Key<?>, Object> binding = new HashMap<Key<?>, Object>();

    private VideoChatSocketManager socketManager;

    private UserService userService;

    private Connection connection;

    private User user;

    @Inject
    public VideoChatSocket(HttpServletRequest request, VideoChatSocketManager socketManager, UserService service) {
        super();
        this.userService = service;
        this.socketManager = socketManager;
        this.binding.put(Key.get(HttpServletRequest.class), request);
    }

    @Inject
    private Provider<User> userProvider;

    @Override
    public void onClose(int closeCode, String message) {
        socketManager.deregisterSocket(this);
        final String statusMessage = new Gson().toJson(new UserOnlineStatusMessage(user.getUserId(), false));
        broadcastMessageToContacts(statusMessage);
    }

    private void broadcastMessageToContacts(final String statusMessage) {
        final Iterable<User> contacts = userService.getContactsList(user.getUserId());
        for (final User contact : contacts) {
            System.out.println("SENDING from " + user.getUserId() + " to " + contact.getUserId()  + " "+ statusMessage);
            final VideoChatSocket socket = socketManager.getSocketForUserId(contact.getUserId());
            try {
                socket.connection.sendMessage(statusMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        this.user = userProvider.get();
        System.out.println("[USER ID]: " + user.getUserId());
        socketManager.registerSocket(this);
        final String message = new Gson().toJson(new UserOnlineStatusMessage(user.getUserId(), true));
        broadcastMessageToContacts(message);
    }

    @Override
    public void onMessage(final String jsonMessage) {
        System.out.println(jsonMessage);
        broadcastMessageToContacts(jsonMessage);
    }

    public int getUserId() {
        return user.getUserId();
    }
}

package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.event.UserOnlineStatusMessage;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gson.Gson;
import com.google.inject.Key;
import com.google.inject.servlet.ServletScopes;

public class VideoChatSocket implements WebSocket.OnTextMessage {

    private final VideoChatSocketManager socketManager;

    private final UserService userService;

    private Connection connection;

    @Inject
    private SessionCtx sessionCtx;

    @Inject
    public VideoChatSocket(VideoChatSocketManager socketManager, UserService service) {
        super();
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
            final List<VideoChatSocket> sockets = socketManager.getSocketForUser(contact);
            if (sockets != null) {
                for (final VideoChatSocket socket : sockets) {
                    try {
                        System.out.println("SENDING from " + sessionCtx.getUser().getEmail() + " to " + contact.getEmail() + " " + statusMessage);
                        socket.connection.sendMessage(statusMessage);
                    } catch (IOException e) {
                        System.out.println("Failed to send message to user " + contact.getEmail() + " cause: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        socketManager.registerSocket(VideoChatSocket.this);
        System.out.println("Opened socket [USER ID]: " + userService.getCurrentUserId());
        final String message = new Gson().toJson(new UserOnlineStatusMessage(userService.getCurrentUserId(), true));
        broadcastMessageToContacts(message);
    }

    @Override
    public void onMessage(final String jsonMessage) {
        executeAsRequestScoped(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println(jsonMessage);
                broadcastMessageToContacts(jsonMessage);
                return true;
            }
        });

    }

    private final Map<Key<?>, Object> bindings = new HashMap<Key<?>, Object>();

    // TODO - move DB stuff here.
    private <T> void executeAsRequestScoped(final Callable<T> command) {
        try {
            ServletScopes.scopeRequest(new Callable<T>() {

                @Inject
                private EntityManager em;

                @Override
                public T call() throws Exception {
                    final T result;
                    try {
                        result = command.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    } finally {
                        em.close();
                    }
                    return result;
                }
            }, bindings).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
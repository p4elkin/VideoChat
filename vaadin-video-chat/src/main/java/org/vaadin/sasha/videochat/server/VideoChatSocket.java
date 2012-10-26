package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.event.UserOnlineStatusMessage;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gson.Gson;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.servlet.ServletScopes;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

public class VideoChatSocket implements WebSocket.OnTextMessage {

    private VideoChatSocketManager socketManager;

    private UserService userService;

    private Connection connection;

    private final User user;

    private final Map<Key<?>, Object> bindings = new HashMap<Key<?>, Object>();

    @Inject
    private Provider<HttpServletRequest> request;
    
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
        executeAsRequestScoped(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final String statusMessage = new Gson().toJson(new UserOnlineStatusMessage(userService.getCurrentUserId(), false));
                broadcastMessageToContacts(statusMessage);
                return true;
            }
        });
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
        bindings.put(Key.get(HttpServletRequest.class), request.get());
        System.out.println("[USER ID]: " + userService.getCurrentUserId());
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

    public User getUser() {
        return user;
    }

    private <T> void executeAsRequestScoped(final Callable<T> command) {
        try {
            ServletScopes.scopeRequest(new Callable<T>() {
                @Inject
                private OObjectDatabaseTx database;

                @Override
                public T call() throws Exception {
                    final T result;
                    try {
                        result = command.call();
                    } catch(Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    finally {
                        database.close();
                    }
                    return result;
                }
            }, bindings).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
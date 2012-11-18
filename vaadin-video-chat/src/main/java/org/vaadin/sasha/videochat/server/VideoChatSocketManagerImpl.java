package org.vaadin.sasha.videochat.server;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Provider;


@Singleton
public class VideoChatSocketManagerImpl implements VideoChatSocketManager {

    private final ListMultimap<User, VideoChatSocket> sockets = ArrayListMultimap.create();

    @Inject
    private Provider<SessionCtx> sessionCtx;

    @Override
    public List<VideoChatSocket> getSocketForUser(User user) {
        return sockets.get(user);
    }

    @Override
    public void registerSocket(VideoChatSocket socket) {
        System.out.println("Registered socket for user " + sessionCtx.get().getUser().getEmail());
        sockets.put(sessionCtx.get().getUser(), socket);
    }

    @Override
    public void deregisterSocket(VideoChatSocket socket) {
        sockets.remove(sessionCtx.get().getUser(), socket);
    }

}

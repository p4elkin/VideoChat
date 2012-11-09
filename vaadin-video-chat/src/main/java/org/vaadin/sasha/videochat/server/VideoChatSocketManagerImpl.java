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
    private Provider<User> userProvider;

    @Override
    public List<VideoChatSocket> getSocketForUser(User user) {
        return sockets.get(user);
    }

    @Override
    public void registerSocket(VideoChatSocket socket) {
        sockets.put(userProvider.get(), socket);
    }

    @Override
    public void deregisterSocket(VideoChatSocket socket) {
        sockets.remove(userProvider.get(), socket);
    }

}

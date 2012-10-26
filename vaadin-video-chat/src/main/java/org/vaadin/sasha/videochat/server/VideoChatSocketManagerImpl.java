package org.vaadin.sasha.videochat.server;

import java.util.List;

import javax.inject.Singleton;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;


@Singleton
public class VideoChatSocketManagerImpl implements VideoChatSocketManager {
    
    private ListMultimap<User, VideoChatSocket> sockets = ArrayListMultimap.create();
    
    @Override
    public List<VideoChatSocket> getSocketForUser(User user) {
        return sockets.get(user);
    }

    @Override
    public void registerSocket(VideoChatSocket socket) {
        sockets.put(socket.getUser(), socket);
    }

    @Override
    public void deregisterSocket(VideoChatSocket socket) {
        sockets.remove(socket.getUser(), socket);
    }

}

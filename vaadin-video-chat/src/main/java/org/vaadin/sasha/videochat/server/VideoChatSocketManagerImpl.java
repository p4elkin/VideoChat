package org.vaadin.sasha.videochat.server;

import javax.inject.Singleton;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;


@Singleton
public class VideoChatSocketManagerImpl implements VideoChatSocketManager {
    
    private BiMap<Integer, VideoChatSocket> socketMap= Maps.synchronizedBiMap(HashBiMap.<Integer, VideoChatSocket>create());
    
    @Override
    public VideoChatSocket getSocketForUserId(int userId) {
        return socketMap.get(userId);
    }

    @Override
    public void registerSocket(VideoChatSocket socket) {
        socketMap.put(socket.getUserId(), socket);
    }

    @Override
    public void deregisterSocket(VideoChatSocket socket) {
        socketMap.inverse().remove(socket);
    }

}

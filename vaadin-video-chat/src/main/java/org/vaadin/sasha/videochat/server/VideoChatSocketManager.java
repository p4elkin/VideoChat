package org.vaadin.sasha.videochat.server;

import java.util.List;

import org.vaadin.sasha.videochat.shared.domain.User;


public interface VideoChatSocketManager {
    
    List<VideoChatSocket> getSocketForUser(User user);
    
    void registerSocket(final VideoChatSocket socket);
    
    void deregisterSocket(final VideoChatSocket socket);
}

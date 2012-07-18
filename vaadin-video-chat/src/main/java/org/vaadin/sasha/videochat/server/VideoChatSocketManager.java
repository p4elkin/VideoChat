package org.vaadin.sasha.videochat.server;


public interface VideoChatSocketManager {
    
    VideoChatSocket getSocketForUserId(int userId);
    
    void registerSocket(final VideoChatSocket socket);
    
    void deregisterSocket(final VideoChatSocket socket);
}

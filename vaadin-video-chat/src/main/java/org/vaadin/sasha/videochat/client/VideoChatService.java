package org.vaadin.sasha.videochat.client;

import java.util.List;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface VideoChatService extends RemoteService {
	
    int createChatRoom(int creatorId) throws IllegalArgumentException;
    
    int signIn(User user) throws IllegalArgumentException;
    
    int authenticate() throws IllegalArgumentException;
    
    int register(User user);
    
    List<String> getUsersOnline();
}

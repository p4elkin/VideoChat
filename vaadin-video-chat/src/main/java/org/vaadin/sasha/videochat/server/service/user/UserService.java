package org.vaadin.sasha.videochat.server.service.user;

import org.vaadin.sasha.videochat.server.User;

public interface UserService {

    User authenticate(String username, String password);
    
    Iterable<User> getContactsList(int userId);
    
    Iterable<User> getUsersOnline(int userId);
    
    void setUserOnline(int id);
    
    void setUserOffline(int id);

    User getUserById(int userId);
}

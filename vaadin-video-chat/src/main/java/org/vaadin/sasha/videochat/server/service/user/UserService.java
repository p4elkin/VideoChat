package org.vaadin.sasha.videochat.server.service.user;

import org.vaadin.sasha.videochat.shared.domain.User;

public interface UserService {

    User signIn(String email);

    User registerUser(User newUser);

    Iterable<User> getContactsList();

    Iterable<User> getUsersOnline();

    void setCurrentUserOnline(boolean isOnline);

    void addToContacts(User user);
    
    Integer getCurrentUserId();

}

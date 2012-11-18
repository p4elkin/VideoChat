package org.vaadin.sasha.videochat.server.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import org.vaadin.sasha.videochat.shared.domain.User;

@Singleton
public class OnlineUsersPool implements Serializable {

    private static List<User> usersOnline = Collections.synchronizedList(new ArrayList<User>());
    
    public void setUserOnline(final User user, boolean isUserOnline) {
        if (isUserOnline && !isUserOnline(user)) {
            usersOnline.add(user);    
        } else {
            usersOnline.remove(user);
        }
        
    }

    public Iterable<User> getUsersOnline() {
        return usersOnline;
    }
    
    public boolean isUserOnline(User user) {
        return usersOnline.contains(user);
    }
    
}

package org.vaadin.sasha.videochat.server.service.user;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.vaadin.sasha.videochat.server.User;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserServiceImpl implements UserService {

    private static List<User> users = Collections.synchronizedList(Lists.<User>newLinkedList());
    
    private static int CURRENT_ID = 0;
    @Override
    public User authenticate(String username, String password) {
        final User user = new User();
        user.setUserId(CURRENT_ID++);
        users.add(user);
        return user;
    }

    @Override
    public Iterable<User> getContactsList(final int userId) {
        return Iterables.<User>filter(users, new Predicate<User>() {
            @Override
            public boolean apply(@Nullable User user) {
                return user.getUserId() != userId;
            }
        });
    }

    @Override
    public Iterable<User> getUsersOnline(int userId) {
        return getContactsList(userId);
    }

    @Override
    public void setUserOnline(int id) {
        
    }

    @Override
    public void setUserOffline(int id) {
        
    }

    @Override
    public User getUserById(final int userId) {
        return Iterables.<User>find(users, new Predicate<User>() {
            @Override
            public boolean apply(@Nullable User user) {
                return user.getUserId() == userId;
            }
        });
    }

}

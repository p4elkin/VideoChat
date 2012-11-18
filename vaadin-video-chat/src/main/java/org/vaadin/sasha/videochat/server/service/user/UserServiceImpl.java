package org.vaadin.sasha.videochat.server.service.user;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.vaadin.sasha.videochat.server.SessionCtx;
import org.vaadin.sasha.videochat.server.persistence.OnlineUsersPool;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserServiceImpl implements UserService {

    @Inject 
    private OnlineUsersPool onlineUsersPool;
    
    @Inject
    private EntityManager em;

    @Inject
    private SessionCtx sessionCtx;

    @Override
    public User signIn(String email) {
        try {
            return em.createQuery("SELECT u FROM User u where u.email = :email", User.class).
            setParameter("email", email).
            getSingleResult();
        } catch (Exception e) {
            System.out.println("Failed to find user by email: " + email + " cause: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<User> getContactsList() {
        final User user = sessionCtx.getUser(); 
        return user != null ? user.getContactList() : Lists.<User>newLinkedList();
    }

    @Override
    public Iterable<User> getUsersOnline() {
        return Iterables.filter(getContactsList(), new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                return onlineUsersPool.isUserOnline(user);
            }
        });
    }

    @Override
    public void setCurrentUserOnline(boolean isOnline) {
        final User current = sessionCtx.getUser();
        if (current != null) {
            onlineUsersPool.setUserOnline(current, true);
        }
    }

    @Override
    public User registerUser(User newUser) {
        em.getTransaction().begin();
        for (final User user : onlineUsersPool.getUsersOnline()) {
            newUser.addContact(user);
        }
        em.persist(newUser);
        em.getTransaction().commit();
        return newUser;
    }

    @Override
    public Integer getCurrentUserId() {
        return sessionCtx.getUser() == null ? -1 : sessionCtx.getUser().getId();
    }

    @Override
    public void addToContacts(User user) {
        final User current = sessionCtx.getUser();
        if (current != null) {
            try {
                em.getTransaction().begin();
                current.addContact(user);
                em.merge(current);
                em.getTransaction().commit();
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to add contact, cause: " + e.getMessage());
            }
        }
    }

}

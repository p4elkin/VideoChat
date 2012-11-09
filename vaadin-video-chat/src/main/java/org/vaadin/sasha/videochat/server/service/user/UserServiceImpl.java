package org.vaadin.sasha.videochat.server.service.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.vaadin.sasha.videochat.server.SessionCtx;
import org.vaadin.sasha.videochat.server.persistence.VideoChatEMF;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.servlet.SessionScoped;


@SessionScoped
public class UserServiceImpl implements UserService {

    private static List<User> usersOnline = Collections.synchronizedList(new ArrayList<User>());

    @Inject
    private VideoChatEMF emf;

    @Inject
    private SessionCtx sessionCtx;

    @Override
    public User authenticate(String email) {
        final EntityManager em = emf.getFactory().createEntityManager();
        User user = null;
        try {
            final TypedQuery<User> q = em.createQuery("select from USER where email = :email", User.class);
            q.setParameter("email", email);
            user = q.getSingleResult();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public Iterable<User> getContactsList() {
        return sessionCtx.getUser().getContactList();
    }

    @Override
    public Iterable<User> getUsersOnline() {
        return Iterables.filter(getContactsList(), new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                return usersOnline.contains(user);
            }
        });
    }

    @Override
    public void setCurrentUserOnline(boolean isOnline) {
        final User current = sessionCtx.getUser();
        if (current != null) {
            if (isOnline && !usersOnline.contains(current)) {
                usersOnline.add(current);
            } else {
                usersOnline.remove(current);
            }
        }
    }

    @Override
    public User registerUser(User newUser) {
        final EntityManager em = emf.getFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newUser);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return newUser;
    }

    @Override
    public Integer getCurrentUserId() {
        return sessionCtx.getUser() == null ? -1 :  sessionCtx.getUser().getId();
    }

}

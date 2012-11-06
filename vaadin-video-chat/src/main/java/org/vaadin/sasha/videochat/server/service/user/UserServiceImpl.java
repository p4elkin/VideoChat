package org.vaadin.sasha.videochat.server.service.user;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.vaadin.sasha.videochat.server.persistence.VideoChatEMF;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.inject.Provider;
import com.google.inject.servlet.SessionScoped;


@SessionScoped
public class UserServiceImpl implements UserService {
    
    @Inject
    private VideoChatEMF emf;
    
    @Inject
    private Provider<User> currentUser;
    
    @Override
    public User authenticate(String username, String password) {
        //final List<User> result = databaseProvider.get().
        //        query(new OSQLSynchQuery<User>("select * from User where userName = '" + 
        //                username +  "' and password = '" + password + "'"));
        return null;// result.get(0);
    }

    @Override
    public Iterable<User> getContactsList() {
        return currentUser.get().getContactList();
    }

    @Override
    public Iterable<User> getUsersOnline() {
        return getContactsList();
    }

    @Override
    public void setUserOnline() {
        
    }

    @Override
    public void setUserOffline() {
        
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
    public int getCurrentUserId() {
        /*ORID orid = databaseProvider.get().getIdentity(currentUser.get());
        if (orid != null) {
            return Long.valueOf(orid.getClusterPosition()).intValue();
        }*/
        return -1;
    }

}

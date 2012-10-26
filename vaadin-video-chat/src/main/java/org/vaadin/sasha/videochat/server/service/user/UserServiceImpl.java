package org.vaadin.sasha.videochat.server.service.user;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.inject.Provider;
import com.google.inject.servlet.SessionScoped;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;


@SessionScoped
public class UserServiceImpl implements UserService {
    
    @Inject
    private HttpSession session;
    
    @Inject
    private Provider<OObjectDatabaseTx> databaseProvider;
    
    @Inject
    private Provider<User> currentUser;
    
    @Override
    public User authenticate(String username, String password) {
        final List<User> result = databaseProvider.get().
                query(new OSQLSynchQuery<User>("select * from User where userName = '" + 
                        username +  "' and password = '" + password + "'"));
        return result.get(0);
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
        final User user = databaseProvider.get().newInstance(User.class);
        user.setUserName(newUser.getUserName());
        user.setPassword(newUser.getPassword());
        databaseProvider.get().save(user);
        return user;
    }

    @Override
    public int getCurrentUserId() {
        ORID orid = databaseProvider.get().getIdentity(currentUser.get());
        if (orid != null) {
            return Long.valueOf(orid.getClusterPosition()).intValue();
        }
        return -1;
    }

}

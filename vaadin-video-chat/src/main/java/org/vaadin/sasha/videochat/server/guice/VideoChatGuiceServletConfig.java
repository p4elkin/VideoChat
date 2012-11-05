package org.vaadin.sasha.videochat.server.guice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.VideoChatServiceImpl;
import org.vaadin.sasha.videochat.server.VideoChatSocket;
import org.vaadin.sasha.videochat.server.VideoChatSocketManager;
import org.vaadin.sasha.videochat.server.VideoChatSocketManagerImpl;
import org.vaadin.sasha.videochat.server.VideoWebSocketServlet;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.server.service.user.UserServiceImpl;
import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;


public class VideoChatGuiceServletConfig extends GuiceServletContextListener {
    
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                EntityManagerFactory emf =  Persistence.createEntityManagerFactory("video_chat");
                
                
                EntityManager em = emf.createEntityManager();
                

                // Create new todo
                em.getTransaction().begin();
                User todo = new User();
                todo.setEmail("test");
                em.persist(todo);
                em.getTransaction().commit();
                
                bind(UserService.class).to(UserServiceImpl.class);
                bind(WebSocket.OnTextMessage.class).to(VideoChatSocket.class);
                bind(VideoChatSocketManager.class).to(VideoChatSocketManagerImpl.class);
                
                serve("/VaadinVideoChat/service").with(VideoChatServiceImpl.class);
                serve("/socket/*").with(VideoWebSocketServlet.class);
                //filter("/*").through(OrientDbFilter.class);
            }
            
            @Provides
            @RequestScoped
            User fetchUser(HttpServletRequest request, UserService service) {
                try {
                    return (User)request.getSession().getAttribute("user");
                } catch (Exception e) {
                    return null;
                }
            }
            
            /*@Provides
            @RequestScoped
            OObjectDatabaseTx getDatabase() {
                OObjectDatabaseTx database = OObjectDatabasePool.global().acquire("remote:localhost/videochat", "root", "root");
                database.getEntityManager().registerEntityClasses("org.vaadin.sasha.videochat.shared.domain");
                return database;    
            }*/
        });
    }
}

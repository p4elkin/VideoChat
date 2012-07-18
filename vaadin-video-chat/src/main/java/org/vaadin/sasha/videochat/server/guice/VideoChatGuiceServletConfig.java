package org.vaadin.sasha.videochat.server.guice;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.User;
import org.vaadin.sasha.videochat.server.VideoChatServiceImpl;
import org.vaadin.sasha.videochat.server.VideoChatSocket;
import org.vaadin.sasha.videochat.server.VideoChatSocketManager;
import org.vaadin.sasha.videochat.server.VideoChatSocketManagerImpl;
import org.vaadin.sasha.videochat.server.VideoWebSocketServlet;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.server.service.user.UserServiceImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

public class VideoChatGuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new VideoChatModule(), new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(UserService.class).to(UserServiceImpl.class);
                bind(WebSocket.OnTextMessage.class).to(VideoChatSocket.class);
                bind(VideoChatSocketManager.class).to(VideoChatSocketManagerImpl.class);
                
                serve("/VaadinVideoChat/greet").with(VideoChatServiceImpl.class);
                serve("/VaadinVideoChat/socket/*").with(VideoWebSocketServlet.class);
            }
            
            @Provides
            @RequestScoped
            User fetchUser(HttpServletRequest request, UserService service) {
                try {
                    int userId = (Integer)request.getSession().getAttribute("userId");
                    return service.getUserById(userId);
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }
}

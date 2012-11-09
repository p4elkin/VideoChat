package org.vaadin.sasha.videochat.server.guice;

import org.eclipse.jetty.websocket.WebSocket;
import org.vaadin.sasha.videochat.server.VideoChatServiceImpl;
import org.vaadin.sasha.videochat.server.VideoChatSocket;
import org.vaadin.sasha.videochat.server.VideoChatSocketManager;
import org.vaadin.sasha.videochat.server.VideoChatSocketManagerImpl;
import org.vaadin.sasha.videochat.server.VideoWebSocketServlet;
import org.vaadin.sasha.videochat.server.persistence.VideoChatEMF;
import org.vaadin.sasha.videochat.server.service.user.UserService;
import org.vaadin.sasha.videochat.server.service.user.UserServiceImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;


public class VideoChatGuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {

                bind(UserService.class).to(UserServiceImpl.class);
                bind(WebSocket.OnTextMessage.class).to(VideoChatSocket.class);
                bind(VideoChatSocketManager.class).to(VideoChatSocketManagerImpl.class);
                bind(VideoChatEMF.class);

                serve("/VaadinVideoChat/service").with(VideoChatServiceImpl.class);
                serve("/socket/*").with(VideoWebSocketServlet.class);
            }
        });
    }
}

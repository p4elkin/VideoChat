package org.vaadin.sasha.videochat.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;

public class VideoChatListener implements ServletContextListener {

    private Server server = null;
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            this.server = new Server(8081);
            VideoChatHandler chatWebSocketHandler = new VideoChatHandler();
            chatWebSocketHandler.setHandler(new DefaultHandler());
            server.setHandler(chatWebSocketHandler);
            server.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}

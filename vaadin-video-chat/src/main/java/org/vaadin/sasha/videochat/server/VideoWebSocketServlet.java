package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import com.google.inject.Provider;

@Singleton
public class VideoWebSocketServlet extends WebSocketServlet {

    @SuppressWarnings("unused")
    private Logger logger = Logger.getLogger("org.vaadin.sasha.videochat.server.VideoWebSocketServlet");
    
    @Inject
    private Provider<WebSocket.OnTextMessage> socketProvider;
    
    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String ignore) {
        return socketProvider.get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(req, resp);
    }
}

package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.vaadin.sasha.videochat.server.event.UserOnlineStatusMessage;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class VideoWebSocketServlet extends WebSocketServlet {

    private Logger logger = Logger.getLogger("org.vaadin.sasha.videochat.server.VideoWebSocketServlet");
    
    private Map<Integer, VideoChatSocket> idToSocket = new ConcurrentHashMap<Integer, VideoChatSocket>();
            
    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String ignore) {
        int userId = -1;
        VideoChatSocket ws = null;
        try {
            logger.log(Level.INFO, "Creating websocket for " + request.getRequestURI());
            final String uri = request.getRequestURI();
            userId = Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));            
        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            ws = new VideoChatSocket(userId);
            idToSocket.put(userId, ws);
        }
        return ws;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(req, resp);
    }
    
    
    public class VideoChatSocket implements WebSocket.OnTextMessage {

        private int userId;
        
        private Connection connection;
        
        public VideoChatSocket(int userId) {
            super();
            this.userId = userId;
        }
        
        @Override
        public void onClose(int arg0, String arg1) {
            for (final Integer id : idToSocket.keySet()) {
                if (idToSocket.get(id) == this) {
                    idToSocket.remove(id);
                }
            }
            
            final String message = new Gson().toJson(new UserOnlineStatusMessage(userId, false));
            final Iterator<Map.Entry<Integer, VideoChatSocket>> it = idToSocket.entrySet().iterator();
            while (it.hasNext()) {
                final Entry<Integer, VideoChatSocket> entry = it.next();
                if (userId != entry.getKey()) {
                    try {
                        entry.getValue().connection.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
        }

        @Override
        public void onOpen(Connection connection) {
            this.connection = connection;
            final String message = new Gson().toJson(new UserOnlineStatusMessage(userId, true));
            final Iterator<Map.Entry<Integer, VideoChatSocket>> it = idToSocket.entrySet().iterator();
            while (it.hasNext()) {
                final Entry<Integer, VideoChatSocket> entry = it.next();
                if (userId != entry.getKey()) {
                    try {
                        entry.getValue().connection.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onMessage(String jsonMessage) {
            System.out.println(jsonMessage);
            final Iterator<Map.Entry<Integer, VideoChatSocket>> it = idToSocket.entrySet().iterator();
            while (it.hasNext()) {
                final Entry<Integer, VideoChatSocket> entry = it.next();
                if (userId != entry.getKey()) {
                    try {
                        entry.getValue().connection.sendMessage(jsonMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

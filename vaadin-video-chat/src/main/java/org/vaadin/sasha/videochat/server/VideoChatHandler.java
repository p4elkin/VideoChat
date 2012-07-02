package org.vaadin.sasha.videochat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

import com.google.gson.Gson;

public class VideoChatHandler extends WebSocketHandler {
    
    private Map<Integer, VideoChatSocket> idToSocket = new HashMap<Integer, VideoChatSocket>();
    
    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
        try {
            final Integer userId = Integer.parseInt(request.getRequestURI().replaceAll("/", ""));
            final VideoChatSocket ws = new VideoChatSocket(userId);
            idToSocket.put(userId, ws);
            return ws;            
        } catch (NumberFormatException ex) {}
        return null;
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
            
        }

        @Override
        public void onOpen(Connection connection) {
            this.connection = connection;
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

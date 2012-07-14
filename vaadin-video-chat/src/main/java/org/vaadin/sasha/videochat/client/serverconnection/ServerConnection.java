package org.vaadin.sasha.videochat.client.serverconnection;

import org.vaadin.sasha.videochat.client.StringUtil;
import org.vaadin.sasha.videochat.client.VideoChatService;
import org.vaadin.sasha.videochat.client.VideoChatServiceAsync;
import org.vaadin.sasha.videochat.client.event.SdpEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent;
import org.vaadin.sasha.videochat.client.event.UserLogedInEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import elemental.client.Browser;
import elemental.events.ErrorEvent;
import elemental.events.Event;
import elemental.events.EventListener;
import elemental.events.MessageEvent;
import elemental.html.WebSocket;
import elemental.js.util.Json;

public class ServerConnection implements SdpEvent.Handler {

    private final VideoChatServiceAsync service = GWT.create(VideoChatService.class);
    
    private final EventBus eventBus;
    
    private WebSocket socket;
    
    private int userId = -1;
    
    @Inject
    public ServerConnection(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.addHandler(SdpEvent.TYPE, this);
    }
    
    public void initialize(final String userName) {
        service.registerUser(userName, new AsyncCallback<Integer>() {
            @Override
            public void onSuccess(Integer id) {
                userId = id;
                socket = createWebSocket();
                eventBus.fireEvent(new UserLogedInEvent(userId));
            }

            @Override
            public void onFailure(Throwable arg0) {
                Browser.getWindow().alert("Failed to register session");
            }
        });
    }

    private WebSocket createWebSocket() {
        final String wsUrl = StringUtil.prepareWsUrl(userId);
        WebSocket ws = Browser.getWindow().newWebSocket(wsUrl);
        ws.setOnopen(new EventListener() {
            @Override
            public void handleEvent(Event evt) {

            }
        });

        ws.setOnclose(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                GWT.log("I am CLOSED!!");
            }
        });

        ws.setOnerror(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                Browser.getWindow().alert(((ErrorEvent) evt).getMessage());
            }
        });

        ws.setOnmessage(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                final MessageEvent messageEvent = (MessageEvent) evt;
                eventBus.fireEvent(new SocketEvent(String.valueOf(messageEvent.getData())));
            }
        });

        return ws;
    }

    @Override
    public void onCandidate(SdpEvent event) {
        socket.send(Json.stringify(event.getMessage()));
    }
}

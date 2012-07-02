package org.vaadin.sasha.videochat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import elemental.client.Browser;
import elemental.dom.Document;
import elemental.dom.LocalMediaStream;
import elemental.dom.MediaStream;
import elemental.events.ErrorEvent;
import elemental.events.Event;
import elemental.events.EventListener;
import elemental.events.MediaStreamEvent;
import elemental.events.MessageEvent;
import elemental.html.DeprecatedPeerConnection;
import elemental.html.IceCandidate;
import elemental.html.Navigator;
import elemental.html.NavigatorUserMediaSuccessCallback;
import elemental.html.SignalingCallback;
import elemental.html.VideoElement;
import elemental.html.WebSocket;
import elemental.html.Window;
import elemental.js.util.JsMappable;
import elemental.util.Mappable;

public class VaadinVideoChat implements EntryPoint {
	
	private final VideoChatServiceAsync service = GWT.create(VideoChatService.class);

	private WebSocket socket;
	
	private Window window;
	
	private Document document;
	
	private VideoElement localVideo;
	
	private VideoElement remoteVideo;
	
	private DeprecatedPeerConnection peerConnection;
	
	private LocalMediaStream localMediaStream = null;
	
	private MediaStream remoteMediaStream = null;
	
	private int roomId = -1;
	
	private int userId = -1;
	      
	private boolean isRoomCreator = false;
	
	private boolean isActive = false;
	
    public void onModuleLoad() {
        this.window = Browser.getWindow();
        this.document = window.getDocument();
        this.localVideo = document.createVideoElement();
        this.remoteVideo = document.createVideoElement();
        
        service.registerUser(new AsyncCallback<Integer>() {
            @Override
            public void onSuccess(Integer id) {
                userId = id;
                socket = createWebSocket();
            }
            @Override
            public void onFailure(Throwable arg0) {
                window.alert("Failed to register session");
            }
        });
        
        final Navigator navigator = window.getNavigator();
        final Mappable map = (Mappable) JsMappable.createObject();
        map.setAt("video", true);
        map.setAt("audio", true);
        
        navigator.webkitGetUserMedia(map, new NavigatorUserMediaSuccessCallback() {

            public boolean onNavigatorUserMediaSuccessCallback(LocalMediaStream stream) {
                localMediaStream = stream;
                localMediaStream.getVideoTracks().item(0);
                localVideo.setSrc(createUrl((JavaScriptObject)localMediaStream));
                localVideo.play();
                return true;
            }
        });

        window.setOnunload(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (localMediaStream != null) {
                    localVideo.setSrc(null);
                    localMediaStream.stop();
                }
            }
        });
        
        localVideo.setWidth(400);
        localVideo.setHeight(250);
        localVideo.setId("localVideo");
        
        remoteVideo.setWidth(400);
        remoteVideo.setHeight(250);
        remoteVideo.setId("remoteVideo");
        
        
        document.getDocumentElement().appendChild(localVideo);
        document.getDocumentElement().appendChild(remoteVideo);
        
        final Button createRoom = new Button("Create room", new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                service.createChatRoom(userId, new AsyncCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer roomId) {
                        VaadinVideoChat.this.roomId = roomId;
                        VaadinVideoChat.this.isRoomCreator = true;
                        initiatePeerConnection();
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        
                    }
                });
            }
        });
        
        RootPanel.get().add(createRoom);
    }
    
    
    private void initiatePeerConnection() {
        peerConnection = createConn("STUN stun.l.google.com:19302", new SignalingCallback() {
            
            @Override
            public boolean onSignalingCallback(String message, DeprecatedPeerConnection source) {
                socket.send(message);
                return false;
            }
        });
        
        peerConnection.setOnconnecting(new EventListener() {
            @Override
            public void handleEvent(Event evt) {/*NOP*/}
        });
        
        peerConnection.setOnaddstream(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                final MediaStreamEvent streamEvent = (MediaStreamEvent)evt;
                final MediaStream stream = streamEvent.getStream();
                remoteVideo.setSrc(createUrl((JavaScriptObject)stream));
                remoteVideo.play();
            }
        });
        
        peerConnection.setOnremovestream(new EventListener() {
            @Override
            public void handleEvent(Event evt) {/*NOP*/}
        });
        
        peerConnection.setOnopen(new EventListener() {
            @Override
            public void handleEvent(Event evt) {/*NOP*/}
        });
        
        peerConnection.addStream(localMediaStream);
        isActive = true;
    }
    
    private void iceCandidateToJSON(IceCandidate candidate) {
        final JSONObject json = new JSONObject();
        json.put("label", new JSONString(candidate.getLabel()));
        json.put("sdp", new JSONString(candidate.toSdp()));
        socket.send(json.toString());
    }
    
    private WebSocket createWebSocket() {
        WebSocket ws = window.newWebSocket("ws://localhost:8081/" + userId);

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
                window.alert(((ErrorEvent)evt).getMessage());
            }
        });
        
        ws.setOnmessage(new EventListener() {
            
            @Override
            public void handleEvent(Event evt) {
                final MessageEvent messageEvent = (MessageEvent)evt;
                if (!isActive && !isRoomCreator) {
                    initiatePeerConnection();
                }
                final String data = String.valueOf(messageEvent.getData());
                try {
                    peerConnection.processSignalingMessage(data);    
                } catch (Exception e) {
                    window.alert(e.getMessage().substring(0, 400));
                }
                
            }
        });

        return ws;
    }
    
    private final native String createUrl(JavaScriptObject stream) /*-{
        return $wnd.webkitURL.createObjectURL(stream);
    }-*/;
    
    private final native DeprecatedPeerConnection createConn(String serverConfiguration, SignalingCallback signalingCallback) /*-{
        return new webkitDeprecatedPeerConnection(serverConfiguration, $entry(signalingCallback.@elemental.html.SignalingCallback::onSignalingCallback(Ljava/lang/String;Lelemental/html/DeprecatedPeerConnection;)).bind(signalingCallback));
    }-*/;

}


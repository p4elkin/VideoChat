package org.vaadin.sasha.videochat.client.serverconnection;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.data.VCandidateMessage;
import org.vaadin.sasha.videochat.client.data.VNegotiationMessage;
import org.vaadin.sasha.videochat.client.data.VSDPMessage;
import org.vaadin.sasha.videochat.client.event.LocalStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.RemoteStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.SdpEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent;
import org.vaadin.sasha.videochat.client.event.UserLogedInEvent;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.event.shared.EventBus;

import elemental.client.Browser;
import elemental.dom.LocalMediaStream;
import elemental.dom.MediaStream;
import elemental.events.Event;
import elemental.events.EventListener;
import elemental.events.MediaStreamEvent;
import elemental.html.IceCallback;
import elemental.html.IceCandidate;
import elemental.html.PeerConnection00;
import elemental.html.SessionDescription;
import elemental.util.Mappable;

public class PeerConnectionManager implements SocketEvent.Handler, UserLogedInEvent.Handler, LocalStreamReceivedEvent.Handler {

    private final static String STUN_SERVER = "STUN stun.l.google.com:19302";

    private EventBus eventBus;

    private boolean isActive = false;

    private int userId;

    private LocalMediaStream localStream;

    private PeerConnection00 peerConnection;

    @Override
    public void onUserLogedIn(UserLogedInEvent event) {
        this.userId = event.getUserId();
        initialize();
    }

    @Inject
    public PeerConnectionManager(final EventBus eventBus, final ServerConnection connection) {
        this.eventBus = eventBus;
        eventBus.addHandler(LocalStreamReceivedEvent.TYPE, this);
        eventBus.addHandler(UserLogedInEvent.TYPE, this);
    }

    public void initialize() {
        initiatePeerConnection();
        this.eventBus.addHandler(SocketEvent.TYPE, this);
    }

    public void call() {
        final Mappable hints = getMediaHints();
        SessionDescription offer = peerConnection.createOffer(hints);
        setLocalDescription(offer, PeerConnection00.SDP_OFFER);
        final VNegotiationMessage message = VNegotiationMessage.create(offer, true);
        Browser.getWindow().getConsole().log("Sending offer" + offer.toSdp());
        eventBus.fireEvent(new SdpEvent(message));
        peerConnection.startIce();
    }

    private final native void setLocalDescription(SessionDescription sdp, int status) /*-{
        var pc = this.@org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager::peerConnection; 
        pc.setLocalDescription(status, sdp);
    }-*/;
    
    private final native void setRemoteDescription(SessionDescription sdp, int status) /*-{
        var pc = this.@org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager::peerConnection; 
        pc.setRemoteDescription(status, sdp);
    }-*/;

    private Mappable getMediaHints() {
        final Mappable hints = (Mappable)JavaScriptObject.createObject();
        hints.setAt("video", true);
        hints.setAt("audio", true);
        return hints;
    }

    private void initiatePeerConnection() {
        peerConnection = Browser.getWindow().newPeerConnection00(STUN_SERVER, new IceCallback() {
            @Override
            public boolean onIceCallback(IceCandidate candidate, boolean moreToFollow, PeerConnection00 source) {
                if (candidate != null) {
                    Browser.getWindow().getConsole().log("Candidate. " + candidate.toSdp());
                    final VCandidateMessage message = VCandidateMessage.create(candidate);
                    eventBus.fireEvent(new SdpEvent(message));
                }
                return moreToFollow;
            }
        });

        peerConnection.setOnaddstream(new EventListener() {

            @Override
            public void handleEvent(Event evt) {
                Browser.getWindow().getConsole().log("Remote stream added.");
                eventBus.fireEvent(new RemoteStreamReceivedEvent(((MediaStreamEvent) evt).getStream()));
            }
        });

        peerConnection.setOnconnecting(new EventListener() {
            
            @Override
            public void handleEvent(Event evt) {
                Browser.getWindow().getConsole().log("Connecting.");
            }
        });

        peerConnection.setOnopen(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                Browser.getWindow().getConsole().log("Connection Open.");
            }
        });

        peerConnection.setOnremovestream(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                Browser.getWindow().getConsole().log("Stream removed.");                
            }
        });
        
        if (localStream != null) {
            Browser.getWindow().getConsole().log("Adding local stream.");
            peerConnection.addStream(localStream);
        }

        isActive = true;
    }

    public void addStream(final MediaStream stream) {
        peerConnection.addStream(stream);
    }

    @Override
    public void onSocketMessage(SocketEvent event) {
        final String msg = event.getJson();
        final VSDPMessage message = VSDPMessage.parse(msg);
        final String type = message.getType();
        if ("candidate".equals(type)) {
            final VCandidateMessage candidateMessage = message.cast();
            IceCandidate candidate = Browser.getWindow().newIceCandidate(candidateMessage.getLabel(), candidateMessage.getSdp());
            peerConnection.processIceMessage(candidate);
        } else if ("offer".equals(type)) {
            final VNegotiationMessage negMessage = message.cast();
            setRemoteDescription(getSessionDescription(negMessage.getSdp()), PeerConnection00.SDP_OFFER);
            answer();
        } else if ("answer".equals(type)) {
            VNegotiationMessage negMessage = message.cast();
            setRemoteDescription(getSessionDescription(negMessage.getSdp()), PeerConnection00.SDP_ANSWER);
        }
    }

    private void answer() {
        final SessionDescription offer = peerConnection.getRemoteDescription();
        SessionDescription answer = peerConnection.createAnswer(offer.toSdp(), getMediaHints());
        Browser.getWindow().getConsole().log("Sending answer: " + answer.toSdp());
        setLocalDescription(answer, PeerConnection00.SDP_ANSWER);
        eventBus.fireEvent(new SdpEvent(VNegotiationMessage.create(answer, false)));
        peerConnection.startIce();
    }

    @Override
    public void onStreamReceived(LocalStreamReceivedEvent event) {
        localStream = event.getStream();
    }
    
    private static SessionDescription getSessionDescription(String sdp) {
        return Browser.getWindow().newSessionDescription(sdp);
    }
}

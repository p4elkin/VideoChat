package org.vaadin.sasha.videochat.client.serverconnection;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.data.VCandidateMessage;
import org.vaadin.sasha.videochat.client.data.VNegotiationMessage;
import org.vaadin.sasha.videochat.client.data.VSessionDescriptionMessage;
import org.vaadin.sasha.videochat.client.event.LocalStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.RemoteStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.SessionDescriptionEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent.SocketHandlerAdapter;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.event.shared.EventBus;

import elemental.client.Browser;
import elemental.dom.LocalMediaStream;
import elemental.events.Event;
import elemental.events.EventListener;
import elemental.events.MediaStreamEvent;
import elemental.html.IceCallback;
import elemental.html.IceCandidate;
import elemental.html.PeerConnection00;
import elemental.html.SessionDescription;
import elemental.util.Mappable;

public class PeerConnectionManager implements LocalStreamReceivedEvent.Handler {

    private final static String STUN_SERVER = "STUN stun.l.google.com:19302";

    private EventBus eventBus;

    private LocalMediaStream localStream;

    private PeerConnection00 peerConnection;

    private SocketHandlerAdapter socketHandler = new SocketHandlerAdapter() {
        
        @Override
        public void onSessionDescriptionMessage(SocketEvent event) {
            final VSessionDescriptionMessage message = event.getJson().cast();
            final String type = message.getSDPType();
            if ("candidate".equals(type)) {
                final VCandidateMessage candidateMessage = message.cast();
                final IceCandidate candidate = Browser.getWindow().newIceCandidate(candidateMessage.getLabel(), candidateMessage.getSdp());
                peerConnection.processIceMessage(candidate);
            } else if ("offer".equals(type)) {
                final VNegotiationMessage negMessage = message.cast();
                initiatePeerConnection();
                setRemoteDescription(getSessionDescription(negMessage.getSdp()), PeerConnection00.SDP_OFFER);
                answer();
            } else if ("answer".equals(type)) {
                final VNegotiationMessage negMessage = message.cast();
                setRemoteDescription(getSessionDescription(negMessage.getSdp()), PeerConnection00.SDP_ANSWER);
            }
        }
    };
    
    @Inject
    public PeerConnectionManager(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.addHandler(LocalStreamReceivedEvent.TYPE, this);
        this.eventBus.addHandler(SocketEvent.TYPE, socketHandler);
    }

    public void call() {
        initiatePeerConnection();
        final Mappable hints = getMediaHints();
        SessionDescription offer = peerConnection.createOffer(hints);
        setLocalDescription(offer, PeerConnection00.SDP_OFFER);
        final VNegotiationMessage message = VNegotiationMessage.create(offer, true);
        Browser.getWindow().getConsole().log("Sending offer" + offer.toSdp());
        eventBus.fireEvent(new SessionDescriptionEvent(message));
        peerConnection.startIce();
    }

    private void answer() {
        final SessionDescription offer = peerConnection.getRemoteDescription();
        final SessionDescription answer = peerConnection.createAnswer(offer.toSdp(), getMediaHints());
        Browser.getWindow().getConsole().log("Sending answer: " + answer.toSdp());
        setLocalDescription(answer, PeerConnection00.SDP_ANSWER);
        eventBus.fireEvent(new SessionDescriptionEvent(VNegotiationMessage.create(answer, false)));
        peerConnection.startIce();
    }
    
    private void initiatePeerConnection() {
        peerConnection = Browser.getWindow().newPeerConnection00(STUN_SERVER, new IceCallback() {
            @Override
            public boolean onIceCallback(IceCandidate candidate, boolean moreToFollow, PeerConnection00 source) {
                if (candidate != null) {
                    Browser.getWindow().getConsole().log("Candidate. " + candidate.toSdp());
                    final VCandidateMessage message = VCandidateMessage.create(candidate);
                    eventBus.fireEvent(new SessionDescriptionEvent(message));
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
    }

    @Override
    public void onStreamReceived(LocalStreamReceivedEvent event) {
        localStream = event.getStream();
    }

    private static SessionDescription getSessionDescription(String sdp) {
        return Browser.getWindow().newSessionDescription(sdp);
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
        final Mappable hints = (Mappable) JavaScriptObject.createObject();
        hints.setAt("video", true);
        hints.setAt("audio", true);
        return hints;
    }
}

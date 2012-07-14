package org.vaadin.sasha.videochat.client.chat;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.event.LocalStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.RemoteStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager;
import org.vaadin.sasha.videochat.client.serverconnection.ServerConnection;
import org.vaadin.sasha.videochat.client.widget.VideoWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.web.bindery.event.shared.EventBus;

import elemental.dom.MediaStream;

public class VideoChatViewImpl extends FlowPanel implements VideoChatView, RemoteStreamReceivedEvent.Handler, LocalStreamReceivedEvent.Handler {

    private VideoWidget localVideo;

    private VideoWidget remoteVideo;

    private final EventBus eventBus;

    private Button connectButton;

    private ServerConnection connection;

    private PeerConnectionManager manager;

    private TextBox loginBox = new TextBox();
    
    @Inject
    public VideoChatViewImpl(final EventBus eventBus, ServerConnection connection, PeerConnectionManager manager, VideoWidget localVideo, VideoWidget remoteVideo) {
        super();
        this.localVideo = localVideo;
        this.remoteVideo = remoteVideo;
        this.connection = connection;
        this.manager = manager;
        this.eventBus = eventBus;
        eventBus.addHandler(RemoteStreamReceivedEvent.TYPE, this);
        eventBus.addHandler(LocalStreamReceivedEvent.TYPE, this);
        construct();
        localVideo.playLocalMedia();
    }

    private void construct() {
        add(localVideo);
        add(remoteVideo);
        localVideo.addStyleName("localVideo fullscreen zoom-in test");

        localVideo.getElement().setId("localVideo");
        remoteVideo.getElement().setId("remoteVideo");

        connectButton = new Button("Connect", new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                manager.call();
            }
        });
        
        loginBox.addStyleName("loginBox");
        connectButton.addStyleName("create-room-button");
        add(connectButton);
        add(loginBox);
        
        loginBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                final String userName = event.getValue();
                if (userName != null && !userName.isEmpty()) {
                    connection.initialize(userName);   
                }
            }
        });
    }

    @Override
    public void onStreamReceived(RemoteStreamReceivedEvent event) {
        final MediaStream stream = event.getStream();
        remoteVideo.setMediaStream(stream);
        remoteVideo.play();
        remoteVideo.addStyleName("remoteVideo");
        remoteVideo.addStyleName("zoom-in");

        localVideo.removeStyleName("fullscreen");
        localVideo.removeStyleName("zoom-in");
        shrinkLocalVideo();
        connectButton.setVisible(false);
    }

    private void shrinkLocalVideo() {
        int heigth = localVideo.getVideoHeight();
        int width = localVideo.getVideoHeight();
        double ratio = (width * 1d) / (heigth * 1d);
        localVideo.setWidth("200px");
        localVideo.setHeight((int) (200d * ratio) + "px");
    }

    @Override
    public void onStreamReceived(LocalStreamReceivedEvent event) {
        //manager.addStream(event.getStream());
    }
}

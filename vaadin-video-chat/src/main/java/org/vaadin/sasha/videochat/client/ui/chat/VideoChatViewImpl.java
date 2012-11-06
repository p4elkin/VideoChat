package org.vaadin.sasha.videochat.client.ui.chat;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.ui.dialog.DialogAction;
import org.vaadin.sasha.videochat.client.ui.dialog.IncomingCallDialogView;
import org.vaadin.sasha.videochat.client.widget.VideoWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Provider;

import elemental.dom.MediaStream;

public class VideoChatViewImpl extends FlowPanel implements VideoChatView {
    
    private VideoWidget localVideo;

    private VideoWidget remoteVideo;

    private Presenter presenter;
    
    @Inject
    private Provider<IncomingCallDialogView> dialogProvider;
    
    private Button connectButton = new Button("Connect", new ClickHandler() {
        @Override
        public void onClick(ClickEvent arg0) {
            presenter.makeCall();
        }
    });
    
    @Inject
    public VideoChatViewImpl(VideoWidget localVideo, VideoWidget remoteVideo) {
        super();
        this.localVideo = localVideo;
        this.remoteVideo = remoteVideo;
        localVideo.playLocalMedia();
        construct();
    }

    private void construct() {
        add(localVideo);
        add(remoteVideo);
        localVideo.addStyleName("localVideo fullscreen zoom-in test");

        localVideo.getElement().setId("localVideo");
        remoteVideo.getElement().setId("remoteVideo");
        
        connectButton.addStyleName("create-room-button");
        add(connectButton);
    }


    private void shrinkLocalVideo() {
        int heigth = localVideo.getVideoHeight();
        int width = localVideo.getVideoHeight();
        double ratio = (width * 1d) / (heigth * 1d);
        localVideo.setWidth("200px");
        localVideo.setHeight((int) (200d * ratio) + "px");
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRemoteStreamedReceived(MediaStream remoteStream) {
        remoteVideo.setMediaStream(remoteStream);
        remoteVideo.play();
        remoteVideo.addStyleName("remoteVideo");
        remoteVideo.addStyleName("fullscreen");
        remoteVideo.addStyleName("zoom-in");

        localVideo.removeStyleName("fullscreen");
        localVideo.removeStyleName("zoom-in");
        shrinkLocalVideo();
        connectButton.setVisible(false);        
    }

    @Override
    public void showIncomingCallDialog(DialogAction acceptAction, DialogAction rejectAction) {
        final IncomingCallDialogView dialogView = dialogProvider.get();
        dialogView.setAcceptCallAction(acceptAction);
        dialogView.setRejectCallAction(rejectAction);
        dialogView.show();
    }
}

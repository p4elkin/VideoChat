package org.vaadin.sasha.videochat.client.chat;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.event.RemoteStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.UserLogedInEvent;
import org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager;
import org.vaadin.sasha.videochat.client.serverconnection.ServerConnection;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class VideoChatActivity extends AbstractActivity implements VideoChatView.Presenter, 
    RemoteStreamReceivedEvent.Handler {

    private VideoChatView view;
    
    private PeerConnectionManager manager;
    
    private EventBus eventBus;
    
    private HandlerRegistration remoteStreamReceivedEventRegistration = null;
    
    @Inject
    public VideoChatActivity(final VideoChatView view, final PeerConnectionManager manager) {
        this.view = view;
        this.manager = manager;
        this.view.setPresenter(this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        if (remoteStreamReceivedEventRegistration != null) {
            remoteStreamReceivedEventRegistration.removeHandler();
        }
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view.asWidget());
        this.eventBus = eventBus;
        remoteStreamReceivedEventRegistration = this.eventBus.addHandler(RemoteStreamReceivedEvent.TYPE, this);
    }

    @Override
    public void makeCall() {
        manager.call();
    }

    @Override
    public void onStreamReceived(RemoteStreamReceivedEvent event) {
        view.onRemoteStreamedReceived(event.getStream());
    }
}

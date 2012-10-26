package org.vaadin.sasha.videochat.client.chat;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.dialog.DialogAction;
import org.vaadin.sasha.videochat.client.dialog.DialogView;
import org.vaadin.sasha.videochat.client.event.RemoteStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent.SocketHandlerAdapter;
import org.vaadin.sasha.videochat.client.message.VNegotiationMessage;
import org.vaadin.sasha.videochat.client.message.VSessionDescriptionMessage;
import org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class VideoChatActivity extends AbstractActivity implements VideoChatView.Presenter, RemoteStreamReceivedEvent.Handler {

    private VideoChatView view;

    private PeerConnectionManager manager;

    @Inject
    public VideoChatActivity(final VideoChatView view, final PeerConnectionManager manager) {
        this.view = view;
        this.manager = manager;
        this.view.setPresenter(this);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view.asWidget());
        eventBus.addHandler(RemoteStreamReceivedEvent.TYPE, this);
        eventBus.addHandler(SocketEvent.TYPE, new SocketHandlerAdapter() {
            @Override
            public void onSessionDescriptionMessage(SocketEvent event) {
                final VSessionDescriptionMessage message = event.getJson().cast();
                final String type = message.getSDPType();
                if ("offer".equals(type)) {
                    view.showIncomingCallDialog(new DialogAction() {
                        @Override
                        public void execute(DialogView dialog) {
                            manager.acceptCall((VNegotiationMessage) message);
                            dialog.hide();
                        }
                    }, new DialogAction() {
                        @Override
                        public void execute(DialogView dialog) {
                            dialog.hide();
                        }
                    });
                }
            }
        });
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

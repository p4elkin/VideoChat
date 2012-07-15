package org.vaadin.sasha.videochat.client.contacts;

import java.util.List;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.VideoChatServiceAsync;
import org.vaadin.sasha.videochat.client.event.SocketEvent;
import org.vaadin.sasha.videochat.client.event.SocketEvent.SocketHandlerAdapter;
import org.vaadin.sasha.videochat.client.message.VContactListMessage;
import org.vaadin.sasha.videochat.client.message.VUserOnlineStatusMessage;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

import elemental.client.Browser;

public class ContactsPresenter implements ContactsView.Presenter {

    private ContactsView view;
    
    private VideoChatServiceAsync service;
    
    private SocketHandlerAdapter handler = new SocketHandlerAdapter() {
        @Override
        public void onContactListMessage(SocketEvent event) {
            final VContactListMessage message = event.getJson().cast();
            if (message.getContactMessageType().equals("ONLINE_STATUS")) {
                VUserOnlineStatusMessage onlineMsg = message.cast();
                view.userOnlineStatusChanged(onlineMsg.getUserId(), onlineMsg.isOnline());
            }
        }
    };
    
    @Inject
    public ContactsPresenter(VideoChatServiceAsync service, EventBus eventBus) {
        this.service = service;
        eventBus.addHandler(SocketEvent.TYPE, handler);
        Browser.getWindow().alert("Creating contacts presenter!");
    }

    @Override
    public void setView(ContactsView view) {
        this.view = view;
    }
    
    @Override
    public void loadContacts() {
        service.getUsersOnline(new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                view.setContacts(result);
            }
            
            @Override
            public void onFailure(Throwable caught) {}
        });
    }
}

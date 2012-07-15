package org.vaadin.sasha.videochat.client.contacts;

import java.util.List;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.client.VideoChatServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContactsPresenter implements ContactsView.Presenter {

    private ContactsView view;
    
    private VideoChatServiceAsync service;
    
    @Inject
    public ContactsPresenter(ContactsView view, final VideoChatServiceAsync service) {
        this.service = service;
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

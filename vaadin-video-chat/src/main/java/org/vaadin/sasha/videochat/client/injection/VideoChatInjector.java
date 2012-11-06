package org.vaadin.sasha.videochat.client.injection;

import org.vaadin.sasha.videochat.client.VideoChatServiceAsync;
import org.vaadin.sasha.videochat.client.framework.activity.VideoChatActivityMapper;
import org.vaadin.sasha.videochat.client.ui.chat.VideoChatView;
import org.vaadin.sasha.videochat.client.ui.contacts.ContactsView;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

@GinModules(VideoChatModule.class)
public interface VideoChatInjector extends Ginjector {

    PlaceController getPlaceController();
    
    EventBus getEventBus();
    
    SimplePanel getMainViewport();
    
    VideoChatActivityMapper getVideoChatActivityMapper();
    
    PlaceHistoryHandler getPlaceHistoryHandler();
    
    VideoChatServiceAsync getRemoteService();
    
    VideoChatView getMainView();
    
    ContactsView getContactsView();
}

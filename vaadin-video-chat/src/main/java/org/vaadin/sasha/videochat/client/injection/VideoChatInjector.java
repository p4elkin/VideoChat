package org.vaadin.sasha.videochat.client.injection;

import org.vaadin.sasha.videochat.client.activity.VideoChatActivityMapper;
import org.vaadin.sasha.videochat.client.chat.VideoChatView;

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
    
    VideoChatView getMainView();
    
    SimplePanel getMainViewport();
    
    VideoChatActivityMapper getVideoChatActivityMapper();
    
    PlaceHistoryHandler getPlaceHistoryHandler();
}

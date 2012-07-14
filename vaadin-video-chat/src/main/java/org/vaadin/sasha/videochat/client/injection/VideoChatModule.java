package org.vaadin.sasha.videochat.client.injection;

import org.vaadin.sasha.videochat.client.VideoChatActivityMapper;
import org.vaadin.sasha.videochat.client.VideoChatPlaceHistoryMapper;
import org.vaadin.sasha.videochat.client.chat.VideoChatActivity;
import org.vaadin.sasha.videochat.client.chat.VideoChatView;
import org.vaadin.sasha.videochat.client.chat.VideoChatViewImpl;
import org.vaadin.sasha.videochat.client.login.LoginActivity;
import org.vaadin.sasha.videochat.client.login.LoginPlace;
import org.vaadin.sasha.videochat.client.login.LoginView;
import org.vaadin.sasha.videochat.client.login.LoginViewImpl;
import org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager;
import org.vaadin.sasha.videochat.client.serverconnection.ServerConnection;
import org.vaadin.sasha.videochat.client.widget.VideoWidget;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class VideoChatModule extends AbstractGinModule {

    @Override
    protected void configure() {
        // Widgets
        bind(VideoWidget.class);
    
        // Activities
        bind(VideoChatActivity.class);
        bind(LoginActivity.class);

        // Views (singletons)
        bind(VideoChatView.class).to(VideoChatViewImpl.class).in(Singleton.class);
        bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
        
        bind(SimplePanel.class).in(Singleton.class);
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(PlaceController.class).in(Singleton.class);
        
        bind(ActivityMapper.class).to(VideoChatActivityMapper.class).in(Singleton.class);
        bind(PlaceHistoryMapper.class).to(VideoChatPlaceHistoryMapper.class).in(Singleton.class);
        
        bind(ServerConnection.class).in(Singleton.class);
        bind(PeerConnectionManager.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public PlaceController getPlaceController(EventBus eventBus) {
        return new PlaceController(eventBus);
    }

    @Provides
    @Singleton
    public ActivityManager getActivityManager(ActivityMapper mapper, EventBus eventBus, HasOneWidget display) {
        ActivityManager activityManager = new ActivityManager(mapper, eventBus);
        activityManager.setDisplay(display);
        return activityManager;
    }
    
    @Provides
    @Singleton
    public PlaceHistoryHandler getHistoryHandler(PlaceController placeController, PlaceHistoryMapper historyMapper, EventBus eventBus,
            ActivityManager activityManager) {
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, new LoginPlace());
        return historyHandler;
    }
}

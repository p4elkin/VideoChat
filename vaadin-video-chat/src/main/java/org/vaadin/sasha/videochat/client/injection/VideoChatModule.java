package org.vaadin.sasha.videochat.client.injection;

import org.vaadin.sasha.videochat.client.serverconnection.PeerConnectionManager;
import org.vaadin.sasha.videochat.client.serverconnection.ServerConnection;
import org.vaadin.sasha.videochat.client.view.MainView;
import org.vaadin.sasha.videochat.client.widget.VideoWidget;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class VideoChatModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(MainView.class);
        bind(VideoWidget.class);
        bind(ServerConnection.class).in(Singleton.class);
        bind(PeerConnectionManager.class).in(Singleton.class);
    }
}

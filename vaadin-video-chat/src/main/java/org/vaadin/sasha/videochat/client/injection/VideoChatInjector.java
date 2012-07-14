package org.vaadin.sasha.videochat.client.injection;

import org.vaadin.sasha.videochat.client.view.MainView;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;

@GinModules(VideoChatModule.class)
public interface VideoChatInjector extends Ginjector {

    EventBus getEventBus();
    
    MainView getMainView();
}

package org.vaadin.sasha.videochat.client.injection;

import javax.inject.Inject;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceController.DefaultDelegate;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

public class PlaceControllerProvider implements Provider<PlaceController> { 
    private final EventBus eventBus;

    @Inject
    public PlaceControllerProvider(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public PlaceController get() {
        return new PlaceController(eventBus, new DefaultDelegate() {
            
        });
    }
}

package org.vaadin.sasha.videochat.client;

import org.vaadin.sasha.videochat.client.injection.VideoChatInjector;
import org.vaadin.sasha.videochat.client.login.LoginPlace;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class VaadinVideoChat implements EntryPoint {

    private VideoChatInjector injector = GWT.create(VideoChatInjector.class);
    
    public void onModuleLoad() {
        final SimplePanel mainViewport = injector.getMainViewport();
        mainViewport.setHeight("100%");
        mainViewport.setWidth("100%");
        RootPanel.get().add(mainViewport);
        
        injector.getPlaceController().goTo(new LoginPlace());
    }

}

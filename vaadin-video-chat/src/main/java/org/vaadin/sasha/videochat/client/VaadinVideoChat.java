package org.vaadin.sasha.videochat.client;

import org.vaadin.sasha.videochat.client.injection.VideoChatInjector;
import org.vaadin.sasha.videochat.client.view.MainView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class VaadinVideoChat implements EntryPoint {

    private VideoChatInjector injector = GWT.create(VideoChatInjector.class);
    
    public void onModuleLoad() {
        final MainView view = injector.getMainView();
        view.setHeight("100%");
        view.setWidth("100%");
        RootPanel.get().add(view);
    }

}

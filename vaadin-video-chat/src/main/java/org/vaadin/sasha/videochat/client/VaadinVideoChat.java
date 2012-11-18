package org.vaadin.sasha.videochat.client;

import org.vaadin.sasha.videochat.client.event.UserLogedInEvent;
import org.vaadin.sasha.videochat.client.injection.VideoChatInjector;
import org.vaadin.sasha.videochat.client.ui.chat.VideoChatPlace;
import org.vaadin.sasha.videochat.client.ui.login.LoginPlace;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class VaadinVideoChat implements EntryPoint {

    private final VideoChatInjector injector = GWT.create(VideoChatInjector.class);

    @Override
    public void onModuleLoad() {
        final SimplePanel mainViewport = injector.getMainViewport();
        injector.getPlaceHistoryHandler();
        mainViewport.addStyleName("videochat");
        RootPanel.get().add(mainViewport);
        RootPanel.get().add(injector.getContactsView().asWidget());
        injector.getRemoteService().authenticate(new AsyncCallback<Integer>() {
            @Override
            public void onSuccess(Integer userId) {
                if (userId > 0) {
                    injector.getEventBus().fireEvent(new UserLogedInEvent(userId));
                    injector.getPlaceController().goTo(new VideoChatPlace());                    
                } else {
                    injector.getPlaceController().goTo(new LoginPlace(false));    
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                injector.getPlaceController().goTo(new LoginPlace(false));
            }
        });
        
        injector.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
            @Override
            public void onPlaceChange(PlaceChangeEvent event) {
                
            }
        });
        
    }

}

package org.vaadin.sasha.videochat.client.chat;

import com.google.gwt.user.client.ui.IsWidget;

import elemental.dom.MediaStream;

public interface VideoChatView extends IsWidget{

    void setPresenter(final Presenter presenter);

    void onRemoteStreamedReceived(MediaStream remoteStream);
    
    interface Presenter {

        void makeCall();
        
    }
}

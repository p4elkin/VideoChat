package org.vaadin.sasha.videochat.client.ui.chat;

import org.vaadin.sasha.videochat.client.ui.dialog.DialogAction;

import com.google.gwt.user.client.ui.IsWidget;

import elemental.dom.MediaStream;

public interface VideoChatView extends IsWidget{

    void setPresenter(final Presenter presenter);

    void onRemoteStreamedReceived(MediaStream remoteStream);
    
    void showIncomingCallDialog(DialogAction acceptAction, DialogAction rejectAction);
    
    interface Presenter {

        void makeCall();
        
    }
}

package org.vaadin.sasha.videochat.client.chat.dialog;

import org.vaadin.sasha.videochat.client.dialog.DialogAction;
import org.vaadin.sasha.videochat.client.dialog.DialogView;


public interface IncomingCallDialogView extends DialogView {

    void setAcceptCallAction(DialogAction action);
    
    void setRejectCallAction(DialogAction action);
    
}

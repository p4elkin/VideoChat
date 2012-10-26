package org.vaadin.sasha.videochat.client.chat.dialog;

import org.vaadin.sasha.videochat.client.dialog.DialogAction;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class IncomingCallDialogViewImpl extends FlowPanel implements IncomingCallDialogView {
    
    private Label callLabel = new Label();
    
    private Button acceptButton = new Button("Accept", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if (acceptAction != null) {
                acceptAction.execute(IncomingCallDialogViewImpl.this);
            }
        }
    });
    
    private Button rejectButton = new Button("Reject", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if (rejectAction != null) {
                rejectAction.execute(IncomingCallDialogViewImpl.this);
                hide();
            }
        }
    });
    
    private DialogAction acceptAction = null;
    
    private DialogAction rejectAction = null;
    
    public IncomingCallDialogViewImpl() {
        addStyleName("dialog");
        add(callLabel);
        add(acceptButton);
        add(rejectButton);
    }

    @Override
    public void setAcceptCallAction(DialogAction action) {
        this.acceptAction = action;
    }

    @Override
    public void setRejectCallAction(DialogAction action) {
        this.rejectAction = action;
    }

    @Override
    public void show() {
        RootPanel.get().add(this);
        //acceptAction.execute(this);
    }

    @Override
    public void hide() {
        RootPanel.get().remove(this);
    }
   
}

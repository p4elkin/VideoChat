package org.vaadin.sasha.videochat.client.widget.dialog.confirmation;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ConfirmationDialog extends DialogBox implements ConfirmationDialogView {

    interface ConfirmationDialogUIBinder extends UiBinder<Widget, ConfirmationDialog> {}
 
    private static ConfirmationDialogUIBinder dialogBinder = GWT.create(ConfirmationDialogUIBinder.class);
    
    @UiField Button confirm = new Button("OK");
    
    @UiField Button cancel = new Button("Cancel");
    
    @UiField HTML message = new HTML();
    
    public ConfirmationDialog() {
        setWidget(dialogBinder.createAndBindUi(this));
    }
    
    @UiHandler("confirm")
    void handleConfirmation(ClickEvent e) {
        fireEvent(new ConfirmationEvent(true));
    }
    
    @UiHandler("cancel")
    void handleCancellation(ClickEvent e) {
        fireEvent(new ConfirmationEvent(false));
    }

    @Override
    public void setMessage(String msg) {
        message.setHTML(msg);
    }

    @Override
    public Button getOkButton() {
        return confirm;
    }

    @Override
    public Button getCancelButton() {
        return cancel;
    }
    
}

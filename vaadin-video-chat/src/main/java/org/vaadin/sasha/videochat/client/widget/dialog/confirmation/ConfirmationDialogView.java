package org.vaadin.sasha.videochat.client.widget.dialog.confirmation;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;

public interface ConfirmationDialogView extends IsWidget {

    Button getOkButton();
    
    Button getCancelButton();
    
    void setMessage(final String message);
    
    interface Presenter {
        
        void addConfirmationHandler(ConfirmationEvent.Handler handler);
    }
}

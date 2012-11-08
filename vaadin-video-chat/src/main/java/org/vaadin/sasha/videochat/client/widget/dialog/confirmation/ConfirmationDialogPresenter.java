package org.vaadin.sasha.videochat.client.widget.dialog.confirmation;

import javax.inject.Inject;

public class ConfirmationDialogPresenter implements ConfirmationDialogView.Presenter {

    private ConfirmationDialogView view;
    
    @Inject
    public ConfirmationDialogPresenter(final ConfirmationDialogView view) {
        this.view = view;
    }
    
    @Override
    public void addConfirmationHandler(ConfirmationEvent.Handler handler) {
        view.asWidget().addHandler(handler, ConfirmationEvent.TYPE);
    }

}

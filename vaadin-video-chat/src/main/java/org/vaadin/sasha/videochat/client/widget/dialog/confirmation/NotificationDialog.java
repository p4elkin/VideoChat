package org.vaadin.sasha.videochat.client.widget.dialog.confirmation;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

public class NotificationDialog extends DialogBox {

    private int delay = 0;
    
    public NotificationDialog() {
        this("", 1000);
    }
    
    public NotificationDialog(String msg) {
        this(msg, 1000);
    }
    
    public NotificationDialog(String msg, int delay) {
        center();
        setModal(true);
        this.delay = delay;
        setMessage(msg);
    }
    
    @Override
    protected void onLoad() {
        super.onLoad();
        Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
            @Override
            public boolean execute() {
                hide();
                return false;
            }
        }, delay);
    }
    
    private void setMessage(String msg) {
        setWidget(new Label(msg));
    }
}

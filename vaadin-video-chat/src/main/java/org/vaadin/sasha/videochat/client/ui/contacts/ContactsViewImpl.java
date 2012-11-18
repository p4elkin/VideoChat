package org.vaadin.sasha.videochat.client.ui.contacts;

import java.util.List;

import javax.inject.Inject;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;

public class ContactsViewImpl extends FlowPanel implements ContactsView {

    private final Presenter presenter;
    
    private final ListBox usersOnline = new ListBox(true);
    
    @Inject
    public ContactsViewImpl(final ContactsPresenter presenter) {
        super();
        this.presenter = presenter;
        presenter.setView(this);
        add(usersOnline);
        usersOnline.addStyleName("contacts");
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        presenter.loadContacts();
    }
   

    @Override
    public void userOnlineStatusChanged(int userId, boolean online) {
        if (online) {
            usersOnline.addItem(String.valueOf(userId));
        } else {
            for (int i = 0; i < usersOnline.getItemCount(); ++i) {
                if (usersOnline.getItemText(i).equals(String.valueOf(userId))) {
                    usersOnline.removeItem(i);      
                }
            }
        }
    }

    @Override
    public void setContacts(List<User> contacts) {
        for (final User user : contacts) {
            usersOnline.addItem(user.getUserName());   
        }
    }
    
}

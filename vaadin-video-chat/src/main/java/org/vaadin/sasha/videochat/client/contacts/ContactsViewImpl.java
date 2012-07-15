package org.vaadin.sasha.videochat.client.contacts;

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;

public class ContactsViewImpl extends FlowPanel implements ContactsView {

    private final Presenter presenter;
    
    private final ListBox usersOnline = new ListBox(true);
    
    @Inject
    public ContactsViewImpl(final ContactsPresenter presenter) {
        super();
        this.presenter = presenter;
        add(usersOnline);
        usersOnline.addStyleName("contacts");
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        presenter.loadContacts();
    }
    
    @Override
    public void setContacts(List<String> result) {
        for (final String userName : result) {
            usersOnline.addItem(userName);   
        }
    }
    
}

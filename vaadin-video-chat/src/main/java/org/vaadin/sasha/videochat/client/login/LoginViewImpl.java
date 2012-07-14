package org.vaadin.sasha.videochat.client.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

public class LoginViewImpl extends FlowPanel implements LoginView {

    private Presenter presenter;
    
    private Button loginButton = new Button("Log in");
    
    private TextBox userNameField = new TextBox();
    
    public LoginViewImpl() {
        super();
        add(userNameField);
        add(loginButton);
        loginButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String userName = userNameField.getValue();
                if (userName != null && !userName.isEmpty()) {
                    presenter.login(userName, null);   
                }
            }
        });
    }
    
    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}

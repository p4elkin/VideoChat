package org.vaadin.sasha.videochat.client.ui.login;

import org.vaadin.sasha.videochat.client.widget.LabeledTextBox;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends Composite implements LoginView {

    public interface LoginViewUIBidner extends UiBinder<Widget, LoginViewImpl> {
    }

    private LoginViewUIBidner binder = GWT.create(LoginViewUIBidner.class);

    private Presenter presenter;

    @UiField
    Button signIn;

    @UiField
    Button register;

    @UiField
    LabeledTextBox userName;

    @UiField
    LabeledTextBox email;

    private boolean isRegistering = false;

    public LoginViewImpl() {
        initWidget(binder.createAndBindUi(this));
        userName.setVisible(false);
    }

    @UiHandler("signIn")
    void doSignIn(ClickEvent e) {
        if (isRegistering) {
            presenter.register();
        } else {
            presenter.signIn();
        }
    }

    @UiHandler("userName")
    public void handleUserNameChange(ValueChangeEvent<String> event) {
        presenter.setUserName(event.getValue());
    }

    @UiHandler("email")
    public void handleEmailChange(ValueChangeEvent<String> event) {
        presenter.setEmail(event.getValue());
    }

    @UiHandler("register")
    public void onClick(ClickEvent event) {
        isRegistering = true;
        userName.setVisible(true);
        register.setVisible(false);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}

package org.vaadin.sasha.videochat.client.ui.login;

import org.vaadin.sasha.videochat.client.widget.LabeledTextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends ComplexPanel implements LoginView {

    private Presenter presenter;
    
    private Button loginButton = new Button("ENTER");
    
    private Button register = new Button("SIGN UP");
    
    private LabeledTextBox userNameField = new LabeledTextBox("Login");
    
    private LabeledTextBox emailField = new LabeledTextBox("Email");
   
    private Element root = DOM.createDiv();
    
    private Element form = DOM.createDiv();
    
    private Element fields = DOM.createDiv();
    
    private Element footer = DOM.createDiv(); 
    
    private boolean isRegistering = false;
    
    public LoginViewImpl() {
        setElement(root);
        addStyleName("login-view");
        footer.addClassName("footer");
        
        add(emailField, fields);
       
        
        root.appendChild(form);
        form.appendChild(fields);
        form.appendChild(footer);
        form.addClassName("login-form");
        
        add(register, footer);
        add(loginButton, footer);
        
        bindHandlers();
    }
    
    private void bindHandlers() {
        userNameField.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                presenter.setUserName(event.getValue());
            }
        });
        
        emailField.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                presenter.setEmail(event.getValue());
            }
        });
        
        loginButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isRegistering) {
                    presenter.register();
                } else {
                    presenter.signIn();
                }
            }
        });
        
        register.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                isRegistering = true;
                add(userNameField, fields);        
            }
        });
        
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
    
    @Override
    public void add(Widget child) {
        super.add(child, form);
    }
    
}

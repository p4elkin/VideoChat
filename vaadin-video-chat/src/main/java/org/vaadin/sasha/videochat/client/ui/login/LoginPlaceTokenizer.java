package org.vaadin.sasha.videochat.client.ui.login;

import org.vaadin.sasha.videochat.client.framework.place.BaseTokenizer;

import com.google.gwt.place.shared.Prefix;

@Prefix("login")
public class LoginPlaceTokenizer extends BaseTokenizer<LoginPlace> {

    @Override
    public LoginPlace getPlace(String token) {
        final String bareToken = extractToken(token);
        return new LoginPlace(bareToken.contains("reg"));
    }

    @Override
    public String getToken(LoginPlace place) {
        return place.toString();
    }
    
}

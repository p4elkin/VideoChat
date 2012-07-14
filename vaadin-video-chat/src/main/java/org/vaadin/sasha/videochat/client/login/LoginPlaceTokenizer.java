package org.vaadin.sasha.videochat.client.login;

import org.vaadin.sasha.videochat.client.place.BaseTokenizer;

import com.google.gwt.place.shared.Prefix;

@Prefix("login")
public class LoginPlaceTokenizer extends BaseTokenizer<LoginPlace> {

    @Override
    public LoginPlace getPlace(String token) {
        return new LoginPlace();
    }

    @Override
    public String getToken(LoginPlace place) {
        return place.toString();
    }

}

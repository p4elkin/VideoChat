package org.vaadin.sasha.videochat.server;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class SessionCtx {

    @Inject
    private HttpSession session;

    public User getUser() {
        try {
            return (User)session.getAttribute("user");
        } catch (Exception e) {
            return null;
        }
    }

    public void setUser(User user) {
        session.setAttribute("user", user);
    }
}

package org.vaadin.sasha.videochat.client.ui.login;

import com.google.gwt.place.shared.Place;

public class LoginPlace extends Place {

    private boolean isRegistering = false;
    
    public LoginPlace(boolean isReg) {
        this.isRegistering = isReg;
    }
    
    public void setRegistering(boolean isRegistering) {
        this.isRegistering = isRegistering;
    }
    
    public boolean isRegistering() {
        return isRegistering;
    }
    
    @Override
    public String toString() {
        return isRegistering ? "reg" : "";
    }
}

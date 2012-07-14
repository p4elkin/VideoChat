package org.vaadin.sasha.videochat.client.util;

import com.google.gwt.core.client.JavaScriptObject;

import elemental.client.Browser;
import elemental.html.Location;


public class StringUtil {

    public static final native String createUrl(JavaScriptObject stream) /*-{
		return $wnd.webkitURL.createObjectURL(stream);
    }-*/;

    public static String prepareWsUrl(int userId) {
        final Location location = Browser.getDocument().getLocation();
        return "ws://" + location.getHost() + "/VaadinVideoChat/socket/" + userId;
    }
}

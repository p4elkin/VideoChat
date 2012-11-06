package org.vaadin.sasha.videochat.client.util;

import com.google.gwt.core.client.JavaScriptObject;

import elemental.client.Browser;
import elemental.html.Location;


public class StringUtil {

    public static final native String createUrl(JavaScriptObject object) /*-{
		return $wnd.webkitURL.createObjectURL(object);
    }-*/;

    public static String prepareWsUrl(int userId) {
        final Location location = Browser.getDocument().getLocation();
        return "ws://" + location.getHost() + "/video-chat/socket/1";
    }
}

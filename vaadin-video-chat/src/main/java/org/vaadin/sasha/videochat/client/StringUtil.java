package org.vaadin.sasha.videochat.client;

import com.google.gwt.core.client.JavaScriptObject;

import elemental.client.Browser;
import elemental.dom.Element;
import elemental.html.Location;

public class StringUtil {

    public static final native String createUrl(JavaScriptObject stream) /*-{
		return $wnd.webkitURL.createObjectURL(stream);
    }-*/;

    public static final void addClassName(String className, Element element) {
        assert (className != null) : "Unexpectedly null class name";

        className = className.trim();
        assert (className.length() != 0) : "Unexpectedly empty class name";

        // Get the current style string.
        String oldClassName = element.getClassName();
        int idx = oldClassName.indexOf(className);

        // Calculate matching index.
        while (idx != -1) {
            if (idx == 0 || oldClassName.charAt(idx - 1) == ' ') {
                int last = idx + className.length();
                int lastPos = oldClassName.length();
                if ((last == lastPos) || ((last < lastPos) && (oldClassName.charAt(last) == ' '))) {
                    break;
                }
            }
            idx = oldClassName.indexOf(className, idx + 1);
        }

        // Only add the style if it's not already present.
        if (idx == -1) {
            if (oldClassName.length() > 0) {
                oldClassName += " ";
            }
            element.setClassName(oldClassName + className);
        }
    }
    
    
    public static final void removeClassName(String className, Element element) {
        assert (className != null) : "Unexpectedly null class name";

        className = className.trim();
        assert (className.length() != 0) : "Unexpectedly empty class name";

        // Get the current style string.
        String oldStyle = element.getClassName();
        int idx = oldStyle.indexOf(className);

        // Calculate matching index.
        while (idx != -1) {
          if (idx == 0 || oldStyle.charAt(idx - 1) == ' ') {
            int last = idx + className.length();
            int lastPos = oldStyle.length();
            if ((last == lastPos)
                || ((last < lastPos) && (oldStyle.charAt(last) == ' '))) {
              break;
            }
          }
          idx = oldStyle.indexOf(className, idx + 1);
        }

        // Don't try to remove the style if it's not there.
        if (idx != -1) {
          // Get the leading and trailing parts, without the removed name.
          String begin = oldStyle.substring(0, idx).trim();
          String end = oldStyle.substring(idx + className.length()).trim();

          // Some contortions to make sure we don't leave extra spaces.
          String newClassName;
          if (begin.length() == 0) {
            newClassName = end;
          } else if (end.length() == 0) {
            newClassName = begin;
          } else {
            newClassName = begin + " " + end;
          }

          element.setClassName(newClassName);
        }
      }

    public static String prepareWsUrl(int userId) {
        final Location location = Browser.getDocument().getLocation();
        return "ws://" + location.getHost() + "/vaadin-video-chat/VaadinVideoChat/socket/" + userId;
    }
}

package org.vaadin.sasha.videochat.client.data;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class VSDPMessage extends JavaScriptObject {

    protected VSDPMessage() {}
    
    public final native String getType() /*-{
        return this.type;
    }-*/;
    
    public static native final VSDPMessage parse(String data) /*-{
		return eval('(' + data + ')');
    }-*/;
    
}

package org.vaadin.sasha.videochat.client.message;

import elemental.html.SessionDescription;

public class VNegotiationMessage extends VSessionDescriptionMessage {

    protected VNegotiationMessage() {}
    
    public final native boolean isOffer() /*-{
        return this.type == 'offer';
    }-*/;
    
    public static native final VNegotiationMessage create(SessionDescription sdp, boolean isOffer) /*-{
        var result = {};
        result.sdp = sdp.toSdp();
        result.type = isOffer ? 'offer' : 'answer';
        result.messageType = 'NEGOTIATION';
        return result;
    }-*/;

    public native final String getSdp() /*-{
        return this.sdp;
    }-*/;
}

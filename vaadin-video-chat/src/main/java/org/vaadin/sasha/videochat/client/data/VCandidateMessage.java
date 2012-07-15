package org.vaadin.sasha.videochat.client.data;

import elemental.html.IceCandidate;

public class VCandidateMessage extends VSessionDescriptionMessage {

    protected VCandidateMessage() {}
    
    public static native final VCandidateMessage create(IceCandidate candidate) /*-{
       var result = {};
       result.messageType = 'NEGOTIATION';
       result.type = 'candidate';
       result.candidate = candidate.toSdp();
       result.label = candidate.label;
       return result;        
    }-*/;

    public native final String getLabel() /*-{
        return this.label;
    }-*/;
    
    public final native String getSdp() /*-{
        return this.candidate;
    }-*/;
}

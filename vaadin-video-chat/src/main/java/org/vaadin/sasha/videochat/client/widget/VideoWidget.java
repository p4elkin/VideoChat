package org.vaadin.sasha.videochat.client.widget;

import org.vaadin.sasha.videochat.client.event.LocalStreamReceivedEvent;
import org.vaadin.sasha.videochat.client.util.StringUtil;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import elemental.client.Browser;
import elemental.dom.LocalMediaStream;
import elemental.dom.MediaStream;
import elemental.html.NavigatorUserMediaSuccessCallback;
import elemental.html.VideoElement;
import elemental.js.util.JsMappable;
import elemental.util.Mappable;

public class VideoWidget extends Widget {

    private VideoElement videoElement;
    
    private MediaStream stream;
    
    private EventBus eventBus;
    
    @Inject
    public VideoWidget(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.videoElement = Browser.getDocument().createVideoElement();
        setElement(castVideo());
    }
    
    public void playLocalMedia() {
        final Mappable map = (Mappable) JsMappable.createObject();
        map.setAt("video", true);
        map.setAt("audio", true);
        Browser.getWindow().getNavigator().webkitGetUserMedia(map, new NavigatorUserMediaSuccessCallback() {
            public boolean onNavigatorUserMediaSuccessCallback(LocalMediaStream stream) {
                setMediaStream(stream);
                play();
                eventBus.fireEvent(new LocalStreamReceivedEvent(stream));
                return true;
            }
        });
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        setMediaStream(null);
        tryStop();
    }
    
    public MediaStream getStream() {
        return stream;
    }
    
    private final native Element castVideo() /*-{
        return this.@org.vaadin.sasha.videochat.client.widget.VideoWidget::videoElement;
    }-*/;
    
    public int getVideoHeight() {
        return videoElement.getVideoHeight();
    }
    
    public int getVideoWidth() {
        return videoElement.getVideoWidth();
    }


    public void setMediaStream(MediaStream mediaStream) {
        this.stream = mediaStream;
        videoElement.setSrc(StringUtil.createUrl((JavaScriptObject)mediaStream));
    }


    public void play() {
        videoElement.play();
    }
    
    public void pause() {
        videoElement.pause();
    }
    
    public void tryStop() {
        if (stream instanceof LocalMediaStream) {
            ((LocalMediaStream)stream).stop();
        }
    }
    
    public VideoElement getVideoElement() {
        return videoElement;
    }
}

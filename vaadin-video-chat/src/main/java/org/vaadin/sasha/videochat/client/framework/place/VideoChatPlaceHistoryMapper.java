package org.vaadin.sasha.videochat.client.framework.place;

import org.vaadin.sasha.videochat.client.ui.chat.VideoChatTokenizer;
import org.vaadin.sasha.videochat.client.ui.login.LoginPlaceTokenizer;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers(value = {LoginPlaceTokenizer.class, VideoChatTokenizer.class})
public interface VideoChatPlaceHistoryMapper extends PlaceHistoryMapper {
   
}

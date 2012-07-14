package org.vaadin.sasha.videochat.client.place;

import org.vaadin.sasha.videochat.client.chat.VideoChatTokenizer;
import org.vaadin.sasha.videochat.client.login.LoginPlaceTokenizer;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers(value = {LoginPlaceTokenizer.class, VideoChatTokenizer.class})
public interface VideoChatPlaceHistoryMapper extends PlaceHistoryMapper {
   
}

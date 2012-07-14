package org.vaadin.sasha.videochat.client;

import org.vaadin.sasha.videochat.client.login.LoginPlaceTokenizer;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers(LoginPlaceTokenizer.class)
public interface VideoChatPlaceHistoryMapper extends PlaceHistoryMapper {
   
}

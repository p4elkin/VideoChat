package org.vaadin.sasha.videochat.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface VideoChatServiceAsync {

	void login(String userName, String password, AsyncCallback<Integer> callback) throws IllegalArgumentException;

	void createChatRoom(int creatorId, AsyncCallback<Integer> callback) throws IllegalArgumentException;
}

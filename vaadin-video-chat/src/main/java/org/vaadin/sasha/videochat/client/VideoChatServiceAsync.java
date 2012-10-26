package org.vaadin.sasha.videochat.client;

import java.util.List;

import org.vaadin.sasha.videochat.shared.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of {@link VideoChatService}.
 */
public interface VideoChatServiceAsync {

	void signIn(User user, AsyncCallback<Integer> callback) throws IllegalArgumentException;

	void createChatRoom(int creatorId, AsyncCallback<Integer> callback) throws IllegalArgumentException;
	
	void getUsersOnline(AsyncCallback<List<String>> callback);
	
	void authenticate(AsyncCallback<Integer> callback) throws IllegalArgumentException;

    void register(User user, AsyncCallback<Integer> asyncCallback);
}

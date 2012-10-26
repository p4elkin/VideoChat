package org.vaadin.sasha.videochat.server;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.vaadin.sasha.videochat.shared.domain.User;



@SuppressWarnings("serial")
public class VideoChatRoomManager implements Serializable {

    private static int USER_ID_COUNTER = 0;

    private static final List<ChatRoom> rooms = new LinkedList<ChatRoom>();

    private static final Map<String, User> users = new ConcurrentHashMap<String, User>();
    
    private static List<User> usersOnline = Collections.synchronizedList(new LinkedList<User>());
    
    private static class ChatRoomFactory {

        private static final ChatRoomFactory INSTANCE = new ChatRoomFactory();

        private static int ID_COUNTER = 0;

        public static ChatRoomFactory get() {
            return INSTANCE;
        }

        private ChatRoomFactory() {}

        final ChatRoom createChatRoom(int initiatorId) {
            return new ChatRoom(ID_COUNTER++, initiatorId);
        }
    }
    
    public static User getUser(String userName) {
        User user = users.get(userName);
        if (user == null) {
            int userId = USER_ID_COUNTER++;
            user = new User();
            user.setUserName(userName);
            users.put(userName, user);
        }
        return user;
    }
    
    public static ChatRoom createRoom(int creatorId) {
        final ChatRoom room = ChatRoomFactory.get().createChatRoom(creatorId);
        rooms.add(room);
        return room;
    }

    public static List<String> getUsersOnline() {
        final List<String> result = new LinkedList<String>();
        for (final User user : usersOnline) {
            result.add(user.getUserName());
        }
        return result;
    }
    
}

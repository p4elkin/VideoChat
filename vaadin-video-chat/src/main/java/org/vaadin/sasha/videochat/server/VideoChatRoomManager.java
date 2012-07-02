package org.vaadin.sasha.videochat.server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class VideoChatRoomManager implements Serializable {

    private static int USER_ID_COUNTER = 0;

    private static final List<ChatRoom> rooms = new LinkedList<ChatRoom>();

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
    
    public static int generateUserId() {
        return USER_ID_COUNTER++;
    }
    
    public static ChatRoom createRoom(int creatorId) {
        final ChatRoom room = ChatRoomFactory.get().createChatRoom(creatorId);
        rooms.add(room);
        return room;
    }
    
}

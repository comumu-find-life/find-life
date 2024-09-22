package com.core.helper;

import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.user.model.User;

public class DirectMessageRoomHelper {

    public static DirectMessageRoom generateDirectMessageRoom(User user1, User user2){
        return DirectMessageRoom.builder()
                .user1(user1)
                .user2(user2)
                .progressHomeId(1L)
                .build();
    }


}

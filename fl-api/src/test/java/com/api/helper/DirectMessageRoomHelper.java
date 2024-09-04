package com.api.helper;

import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.user.model.Role;
import com.core.api_core.user.model.User;
import com.core.chat_core.chat.model.DirectMessage;

import java.time.LocalDateTime;

public class DirectMessageRoomHelper {

    public static DirectMessageRoom generateDirectMessageRoom(){
        return DirectMessageRoom.builder()
                .id(1L)
                .user1(generateGetter())
                .user2(generateProvider())
                .progressHomeId(1L)
                .build();
    }

    public static DirectMessage generateDirectMessage() {
        return DirectMessage.builder()
                .senderId(1L)
                .receiverId(2L)
                .message("lastMessage")
                .sentAt(LocalDateTime.of(2024,10,10,1,1))
                .build();
    }


    private static User generateGetter(){
        return User.builder()
                .id(1L)
                .email("getter@naver.com")
                .role(Role.GETTER)
                .nickname("getter")
                .profileUrl("getterProfile")
                .build();
    }

    private static User generateProvider(){
        return User.builder()
                .id(2L)
                .email("provider@naver.com")
                .role(Role.PROVIDER)
                .nickname("provider")
                .profileUrl("providerProfile")
                .build();
    }
}

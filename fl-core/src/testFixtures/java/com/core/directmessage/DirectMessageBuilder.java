package com.core.directmessage;

import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.user.model.Gender;
import com.core.api_core.user.model.Role;
import com.core.api_core.user.model.User;
import com.core.api_core.chat.model.DirectMessage;

import java.time.LocalDateTime;

public class DirectMessageBuilder {
    public static DirectMessageRoom createDirectMessageRoom(User getter, User provider){
        return DirectMessageRoom.builder()
                .id(1L)
                .user1(getter)
                .user2(provider)
                .progressHomeId(1L)
                .build();
    }

    public static DirectMessage createDirectMessage(String message) {
        return DirectMessage.builder()
                .senderId(1L)
                .receiverId(2L)
                .message(message)
                .sentAt(LocalDateTime.of(2024,10,10,1,1))
                .build();
    }
    public static User createGetter(String encodePassword) {
        return User.builder()
                .id(1L)
                .email("getter@naver.com")
                .nickname("getter") //
                //.role(Role.GETTER)
                .password(encodePassword) //
                .job("student")
                .profileUrl("url")
                .brith(21)
                .phoneNumber(0101231233)
                .gender(Gender.MALE)
                .nationality("KOREAN")
                .build();
    }

    public static User createProvider(String encodePassword){
        return User.builder()
                .id(2L)
                .email("getter@naver.com")
                .nickname("getter") //
                //.role(Role.GETTER)
                .password(encodePassword) //
                .job("student")
                .profileUrl("url")
                .brith(21)
                .phoneNumber(0101231233)
                .gender(Gender.MALE)
                .nationality("KOREAN")
                .build();
    }
}

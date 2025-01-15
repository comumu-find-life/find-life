package com.chatting.service;

import com.common.chat.mapper.DirectMessageMapper;
import com.common.chat.request.DirectMessageRequest;
import com.core.api_core.chat.model.DirectMessage;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.user.model.User;

public class DirectMessageHelper {


    public static DirectMessageRequest createDirectMessageRequest(final String message, final Long senderId, final Long receiverId){
        return  DirectMessageRequest.builder()
                .message(message)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }

    public static DirectMessageRoom createDirectMessageRoom(final User user1,  final User user2, final Long roomId){
        return DirectMessageRoom.builder()
                .user1(user1)
                .user2(user2)
                .progressHomeId(roomId)
                .build();
    }

}

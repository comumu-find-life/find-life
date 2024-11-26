package com.chatting.service;

import com.common.chat.request.DirectMessageRequest;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.user.model.User;

public class DirectMessageHandler {

    public static DirectMessageRequest createDirectMessageRequest(String message, Long senderId, Long receiverId){
        return  DirectMessageRequest.builder()
                .message(message)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }

    public static DirectMessageRoom createDirectMessageRoom(User user1,  User user2, Long roomId){
        return DirectMessageRoom.builder()
                .user1(user1)
                .user2(user2)
                .progressHomeId(roomId)
                .build();
    }

    public static DirectMessageRequest createDealCompletionMessage(DirectMessageRequest request) {
        return DirectMessageRequest.builder()
                .message("DEAL MESSAGE")
                .receiverId(request.getReceiverId())
                .isDeal(3)
                .senderId(request.getSenderId())
                .dealState(DealState.COMPLETE_DEAL)
                .roomId(request.getRoomId())
                .build();
    }
}

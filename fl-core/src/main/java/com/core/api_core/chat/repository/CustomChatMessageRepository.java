package com.core.api_core.chat.repository;

import com.core.api_core.chat.model.DirectMessage;

import java.util.List;
import java.util.Optional;

public interface CustomChatMessageRepository {

    Optional<DirectMessage> findLastMessageByUserIds(Long user1Id, Long user2Id);
    List<DirectMessage> findDirectMessageByUserIds(Long user1Id, Long user2Id);
    int countNotReadMessage(Long senderId, Long receiverId);
    void markMessagesAsRead(Long senderId, Long receiverId);
}

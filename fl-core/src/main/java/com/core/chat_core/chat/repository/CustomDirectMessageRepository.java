package com.core.chat_core.chat.repository;

import com.core.chat_core.chat.model.DirectMessage;

import java.util.Optional;

public interface CustomDirectMessageRepository {
    Optional<DirectMessage> findLastMessage(Long user1Id, Long user2Id);
}

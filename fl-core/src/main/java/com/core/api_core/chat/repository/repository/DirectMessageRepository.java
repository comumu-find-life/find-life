package com.core.api_core.chat.repository.repository;


import com.core.api_core.chat.model.DirectMessage;
import com.core.api_core.chat.repository.CustomChatMessageRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, String>, CustomChatMessageRepository {
}

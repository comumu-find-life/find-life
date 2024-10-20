package com.core.chat_core.chat.repository;


import com.core.chat_core.chat.model.DirectMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, String>, CustomChatMessageRepository{
}

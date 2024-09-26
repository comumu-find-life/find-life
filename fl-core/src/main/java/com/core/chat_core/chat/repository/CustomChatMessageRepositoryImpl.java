package com.core.chat_core.chat.repository;

import com.core.chat_core.chat.model.DirectMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomChatMessageRepositoryImpl implements CustomChatMessageRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<DirectMessage> findLastMessageByUserIds(Long user1Id, Long user2Id) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("senderId").is(user1Id).and("receiverId").is(user2Id),
                Criteria.where("senderId").is(user2Id).and("receiverId").is(user1Id)
        ));
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "sentAt"));
        query.limit(1);

        DirectMessage lastMessage = mongoTemplate.findOne(query, DirectMessage.class);
        return Optional.ofNullable(lastMessage);
    }

    @Override
    public List<DirectMessage> findDirectMessageByUserIds(Long user1Id, Long user2Id) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("senderId").is(user1Id).and("receiverId").is(user2Id),
                Criteria.where("senderId").is(user2Id).and("receiverId").is(user1Id)
        ));
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "sentAt"));

        return mongoTemplate.find(query, DirectMessage.class);
    }
}
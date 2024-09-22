package com.core.chat_core.chat.repository;

import com.core.chat_core.chat.model.DirectMessage;
import com.core.chat_core.chat.model.QDirectMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomDirectMessageRepositoryImpl implements CustomDirectMessageRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<DirectMessage> findLastMessageMyUserIds(Long user1Id, Long user2Id) {
        QDirectMessage directMessage = QDirectMessage.directMessage;

        DirectMessage result = queryFactory
                .selectFrom(directMessage)
                .where(
                        (directMessage.senderId.eq(user1Id).and(directMessage.receiverId.eq(user2Id)))
                                .or(directMessage.senderId.eq(user2Id).and(directMessage.receiverId.eq(user1Id)))
                )
                .orderBy(directMessage.sentAt.desc())
                .fetchFirst(); // fetchFirst()는 결과를 하나만 가져옴

        return Optional.ofNullable(result);
    }

    @Override
    public List<DirectMessage> findDirectMessageByUserIds(Long user1Id, Long user2Id) {
        QDirectMessage directMessage = QDirectMessage.directMessage;

        return queryFactory
                .selectFrom(directMessage)
                .where(
                        (directMessage.senderId.eq(user1Id).and(directMessage.receiverId.eq(user2Id)))
                                .or(directMessage.senderId.eq(user2Id).and(directMessage.receiverId.eq(user1Id)))
                )
                .orderBy(directMessage.sentAt.asc()) // 시간순으로 정렬
                .fetch();
    }
}
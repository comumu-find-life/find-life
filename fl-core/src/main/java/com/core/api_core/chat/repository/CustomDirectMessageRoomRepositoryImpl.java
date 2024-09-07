package com.core.api_core.chat.repository;

import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.model.QDirectMessageRoom;
import com.core.api_core.chat.repository.CustomDirectMessageRoomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomDirectMessageRoomRepositoryImpl implements CustomDirectMessageRoomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomDirectMessageRoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<DirectMessageRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        QDirectMessageRoom directMessageRoom = QDirectMessageRoom.directMessageRoom;

        DirectMessageRoom room = queryFactory
                .selectFrom(directMessageRoom)
                .where(directMessageRoom.user1.id.eq(user1Id)
                        .and(directMessageRoom.user2.id.eq(user2Id)))
                .fetchOne();

        return Optional.ofNullable(room);
    }

    @Override
    public List<DirectMessageRoom> findByUser1IdOrUser2Id(Long userId) {
        QDirectMessageRoom directMessageRoom = QDirectMessageRoom.directMessageRoom;

        return queryFactory
                .selectFrom(directMessageRoom)
                .where(directMessageRoom.user1.id.eq(userId)
                        .or(directMessageRoom.user2.id.eq(userId)))
                .fetch();
    }
}
package com.core.domain.chat.repository;

import com.core.domain.chat.model.DirectMessageRoom;

import java.util.List;
import java.util.Optional;

public interface CustomDirectMessageRoomRepository {

    Optional<DirectMessageRoom> findByUser1IdAndUser2Id(final Long user1Id, final Long user2Id);

    List<DirectMessageRoom> findByUser1IdOrUser2Id(final Long userId);

    void deleteAllByUserId(final Long userId);
}

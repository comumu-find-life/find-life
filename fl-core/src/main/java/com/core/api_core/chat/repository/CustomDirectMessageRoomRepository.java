package com.core.api_core.chat.repository;

import com.core.api_core.chat.model.DirectMessageRoom;

import java.util.List;
import java.util.Optional;

public interface CustomDirectMessageRoomRepository {

    Optional<DirectMessageRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    List<DirectMessageRoom> findByUser1IdOrUser2Id(Long userId);
}

package com.core.chat.repository;

import com.core.chat.model.DirectMessageRoom;

import java.util.Optional;

public interface CustomDirectMessageRoomRepository {
    Optional<DirectMessageRoom> findByUser1IdAndUser2Id();
}

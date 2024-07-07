package com.core.api_core.chat.repository;

import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.CustomDirectMessageRoomRepository;

import java.util.List;
import java.util.Optional;

public class CustomDirectMessageRoomRepositoryImpl implements CustomDirectMessageRoomRepository {

    @Override
    public Optional<DirectMessageRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        return null;
    }

    @Override
    public List<DirectMessageRoom> findByUser1IdOrUser2Id(Long userId) {
        return null;
    }
}

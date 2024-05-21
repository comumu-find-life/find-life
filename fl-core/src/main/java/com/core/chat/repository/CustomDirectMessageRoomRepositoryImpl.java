package com.core.chat.repository;

import com.core.chat.model.DirectMessageRoom;

import java.util.Optional;

public class CustomDirectMessageRoomRepositoryImpl implements  CustomDirectMessageRoomRepository{

    @Override
    public Optional<DirectMessageRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        return null;
    }
}

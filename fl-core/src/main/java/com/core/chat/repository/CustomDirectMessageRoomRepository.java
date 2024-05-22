package com.core.chat.repository;

import com.core.chat.model.DirectMessageRoom;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomDirectMessageRoomRepository {
    @Query("SELECT r FROM DirectMessageRoom r WHERE r.user1.id = :user1Id AND r.user2.id = :user2Id")
    Optional<DirectMessageRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    @Query("SELECT r FROM DirectMessageRoom r WHERE r.user1.id = :userId OR r.user2.id = :userId")
    List<DirectMessageRoom> findByUser1IdOrUser2Id(Long userId);
}

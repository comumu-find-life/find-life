package com.core.domain.chat.repository;

import com.core.domain.chat.model.DirectMessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageRoomRepository extends JpaRepository<DirectMessageRoom, Long>, CustomDirectMessageRoomRepository {
}

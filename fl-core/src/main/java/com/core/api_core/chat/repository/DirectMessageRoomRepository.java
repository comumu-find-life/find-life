package com.core.api_core.chat.repository;

import com.core.api_core.chat.model.DirectMessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageRoomRepository extends JpaRepository<DirectMessageRoom, Long>, CustomDirectMessageRoomRepository {
}

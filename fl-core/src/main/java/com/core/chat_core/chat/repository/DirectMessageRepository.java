package com.core.chat_core.chat.repository;

import com.core.chat_core.chat.model.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long>, CustomDirectMessageRepository {

//    @Query("SELECT d FROM DirectMessage d WHERE (d.senderId = :user1Id AND d.receiverId = :user2Id) OR (d.senderId = :user2Id AND d.receiverId = :user1Id) ORDER BY d.sentAt")
//    List<DirectMessage> findDirectMessageByUserIds(Long user1Id, Long user2Id);

}

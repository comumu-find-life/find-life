//package com.chatting.repository;
//
//
//import com.chatting.model.DirectMessage;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
//
//    @Query("SELECT d FROM DirectMessage d WHERE (d.senderId = :user1Id AND d.receiverId = :user2Id) OR (d.senderId = :user2Id AND d.receiverId = :user1Id) ORDER BY d.sentAt")
//    List<DirectMessage> findRecentLogs(Long user1Id, Long user2Id);
//
//    @Query("SELECT d FROM DirectMessage d WHERE (d.senderId = :user1Id AND d.receiverId = :user2Id) OR (d.senderId = :user2Id AND d.receiverId = :user1Id) ORDER BY d.sentAt DESC")
//    DirectMessage findLastMessage(Long user1Id, Long user2Id);
//}

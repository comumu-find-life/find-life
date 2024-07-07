//package com.chatting.model;
//
//import com.common.chat.response.DirectMessageResponse;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class DirectMessage {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "direct_message_id")
//    private Long id;
//
//    @Column(name = "sender_id")
//    private Long senderId;
//
//    @Column(name = "receiver_id")
//    private Long receiverId;
//
//    @Column(name = "message")
//    private String message;
//
//    @Column(name = "sent_at")
//    private LocalDateTime sentAt;
//
//    public DirectMessageResponse toResponse(){
//        return DirectMessageResponse.builder()
//                .message(this.message)
//                .receiverId(this.receiverId)
//                .senderId(this.senderId)
//                .sentAt(this.sentAt)
//                .build();
//    }
//
//}

package com.core.chat_core.chat.model;


import com.core.api_core.deal.model.DealState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direct_message_id")
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "message")
    private String message;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    private Long dealId;
    /**
     * 0 --> 일반 채팅 메시지
     * 1 --> 안전거래 시작 폼
     * 2 --> 안전거래 진행 중 품
     * 3 --> 안전거래 완료 폼
     */
    private int isDeal;

    @Enumerated(EnumType.STRING)
    private DealState dealState;

}

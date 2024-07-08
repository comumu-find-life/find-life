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

    private boolean isDeal;

    @Enumerated(EnumType.STRING)
    private DealState dealState;

}

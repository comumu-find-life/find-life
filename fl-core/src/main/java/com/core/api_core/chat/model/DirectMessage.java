package com.core.api_core.chat.model;

import com.core.api_core.deal.model.DealState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_message")
public class DirectMessage {

    @Id
    @Column(name = "direct_message_id")
    private String id;

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
     * 4 --> 안전거래 취소 폼
     */
    private int isDeal;

    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private DealState dealState;

}

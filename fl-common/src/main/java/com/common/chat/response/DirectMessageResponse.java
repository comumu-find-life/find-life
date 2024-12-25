package com.common.chat.response;

import com.core.api_core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 메시지 응답 DTO
 */
@Getter
@Builder
public class DirectMessageResponse {
    private String id;
    private Long senderId;
    private Long receiverId;
    private Long dealId;
    private String message;
    private int isDeal;
    private DealState dealState;
    private LocalDateTime sentAt;
}

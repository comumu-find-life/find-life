package com.common.chat.response;

import com.core.api_core.deal.model.DealState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 메시지 응답 DTO
 */
@Getter
@Builder
public class DirectMessageResponse {
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String message;
    private int isDeal;
    private DealState dealState;
    private LocalDateTime sentAt;
}

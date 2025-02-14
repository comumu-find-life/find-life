package com.core.domain.chat.dto;

import com.core.domain.deal.model.DealState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * 메시지 생성 DTO
 */
@Getter
@Builder
public class DirectMessageRequest {
    private Long senderId;
    private Long roomId;
    private Long receiverId;
    private String message;
    @JsonProperty("isDeal")
    private int isDeal;
    private Long dealId;
    private DealState dealState;
}

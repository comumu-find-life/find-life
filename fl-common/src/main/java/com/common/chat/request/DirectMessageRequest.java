package com.common.chat.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 메시지 생성 DTO
 */
@Getter
@Builder
public class DirectMessageRequest {
    private Long senderId;
    private String roomId;
    private Long receiverId;
    private String message;
}

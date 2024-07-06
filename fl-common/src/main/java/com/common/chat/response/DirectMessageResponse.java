package com.common.chat.response;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 메시지 응답 DTO
 */
@Builder
public class DirectMessageResponse {
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String message;
    private LocalDateTime sentAt;
}

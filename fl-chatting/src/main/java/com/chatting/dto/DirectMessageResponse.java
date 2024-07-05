package com.chatting.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class DirectMessageResponse {
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalDateTime sentAt;
}

package com.service.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageDto {

    private Long senderId;
    private String senderName;
    private String roomId;
    private Long receiverId;
    private String message;
}

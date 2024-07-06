package com.chatting.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DirectMessageDto {

    private Long senderId;
    private String senderName;
    private String roomId;
    private Long receiverId;
    private String message;
    private LocalDateTime sentAt;

}

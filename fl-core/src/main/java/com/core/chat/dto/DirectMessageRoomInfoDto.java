package com.core.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageRoomInfoDto {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
}

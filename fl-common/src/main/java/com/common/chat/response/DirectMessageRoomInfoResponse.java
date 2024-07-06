package com.common.chat.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 채팅 목록 응답 DTO
 */
@Getter
@Builder
public class DirectMessageRoomInfoResponse {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
}

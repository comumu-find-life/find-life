package com.common.chat.request;


import lombok.Builder;
import lombok.Getter;

/**
 * 채팅 방 생성 DTO
 */
@Getter
@Builder
public class DirectMessageApplicationRequest {
    private String message;
    private Long receiverId;
    private Long roomId;
}

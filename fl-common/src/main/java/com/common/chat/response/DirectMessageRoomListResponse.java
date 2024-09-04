package com.common.chat.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * todo
 * 사용자 profile, 마지막 메시지 추가
 */
@Getter
@Builder
public class DirectMessageRoomListResponse {
    private Long id;
    private Long userId;
    private String userNickname;
    private String userProfileUrl;
    private Long progressHomeId;
    private String lastMessage;
    private LocalDateTime lastSendDateTime;
}

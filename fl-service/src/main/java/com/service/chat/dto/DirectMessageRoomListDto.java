package com.service.chat.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * todo
 * 사용자 profile, 마지막 메시지 추가
 */
@Getter
@Builder
public class DirectMessageRoomListDto {

    private Long id;
    private Long userId;
    private String userName;
}

package com.service.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageRoomListResponse {

    private Long id;
    private Long userId;
    private String userNickname;
    private String userProfileUrl;
}

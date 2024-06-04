package com.service.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageRoomListDto {

    private Long id;
    private Long userId;
    private String userName;
}

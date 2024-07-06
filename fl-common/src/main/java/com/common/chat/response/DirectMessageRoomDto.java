package com.common.chat.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageRoomDto {

    private Long id;
    private Long user1Id;
    private Long user2Id;
}

package com.core.api_core.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageTotalRequest {
    private Long senderId; // 이걸로 계좌 유무 조회
    private Long receiverId;
    private Long homeId;
    private Long roomId;
}

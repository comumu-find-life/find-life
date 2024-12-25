package com.common.chat.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageReadRequest {
    private final Long senderId;
    private final Long receiverId;
}

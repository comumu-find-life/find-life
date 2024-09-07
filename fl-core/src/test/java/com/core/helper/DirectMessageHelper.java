package com.core.helper;

import com.core.chat_core.chat.model.DirectMessage;

import java.time.LocalDateTime;

public class DirectMessageHelper {

    public static DirectMessage generateDirectMessage(LocalDateTime sentAt) {
        return DirectMessage.builder()
                .senderId(1L)
                .receiverId(2L)
                .message("message time : " + sentAt)
                .sentAt(sentAt)
                .isDeal(0)
                .build();
    }
}

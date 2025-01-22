package com.chatting.controller;


import com.chatting.service.DirectMessageService;
import com.core.api_core.chat.dto.DirectMessageRequest;
import com.core.api_core.chat.dto.DirectMessageResponse;
import com.core.api_core.chat.model.DirectMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class DirectMessageController {

    private final SimpMessagingTemplate template;
    private final DirectMessageService dmService;

    @MessageMapping(value = "/chat/message")
    public void message(final DirectMessageRequest dmDto)  {
        DirectMessage directMessage = dmService.toDirectMessage(dmDto);
        DirectMessageResponse directMessageResponse = dmService.toDirectMessageResponse(directMessage);
        dmService.saveDirectMessageAndPushNotication(directMessage);
        template.convertAndSend("/sub/chat/room/" + dmDto.getRoomId(), directMessageResponse);
    }
}

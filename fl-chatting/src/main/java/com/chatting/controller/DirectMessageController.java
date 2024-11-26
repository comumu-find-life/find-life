package com.chatting.controller;


import com.chatting.service.DirectMessageService;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


/**
 * STOMP 소켓 통신 Controller
 */
@Controller
@RequiredArgsConstructor
public class DirectMessageController {

    private final SimpMessagingTemplate template;
    private final DirectMessageService dmService;

    @MessageMapping(value = "/chat/message")
    public void message(DirectMessageRequest dmDto) throws IllegalAccessException {
        DirectMessageResponse response = dmService.sendDM(dmDto);
        template.convertAndSend("/sub/chat/room/" + dmDto.getRoomId(), response);
    }

}

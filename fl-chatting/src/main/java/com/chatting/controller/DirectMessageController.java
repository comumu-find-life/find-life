package com.chatting.controller;


import com.chatting.service.DirectMessageService;
import com.chatting.service.WebSocketSessionManager;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class DirectMessageController {

    private final WebSocketSessionManager sessionManager;
    private final SimpMessagingTemplate template;
    private final DirectMessageService dmService;


    @MessageMapping(value = "/chat/message")
    public void message(final DirectMessageRequest dmDto)  {
        DirectMessageResponse response = dmService.sendDM(dmDto);
        boolean isReceiverInRoom = sessionManager.isUserInRoom(dmDto.getRoomId(), dmDto.getReceiverId());
        if(isReceiverInRoom) {
            dmService.updateMessageReadStatus(response.getId());
        }
        template.convertAndSend("/sub/chat/room/" + dmDto.getRoomId(), response);
    }

}

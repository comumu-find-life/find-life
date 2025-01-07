package com.chatting.controller;


import com.chatting.service.DirectMessageService;
import com.chatting.service.WebSocketSessionManager;
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

    private final WebSocketSessionManager sessionManager;
    private final SimpMessagingTemplate template;
    private final DirectMessageService dmService;


    @MessageMapping(value = "/chat/message")
    public void message(DirectMessageRequest dmDto) throws IllegalAccessException {
        // 메시지 저장
        DirectMessageResponse response = dmService.sendDM(dmDto);

        // 수신자가 채팅방에 있는지 확인
        boolean isReceiverInRoom = sessionManager.isUserInRoom(dmDto.getRoomId(), dmDto.getReceiverId());

        // 읽음 여부 업데이트
        if(isReceiverInRoom) {
            dmService.updateMessageReadStatus(response.getId());
        }
        // 웹소켓 전송
        template.convertAndSend("/sub/chat/room/" + dmDto.getRoomId(), response);
    }

}

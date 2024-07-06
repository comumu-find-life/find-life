package com.chatting.controller;


import com.chatting.service.DirectMessageService;
import com.common.chat.request.DirectMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class DirectMessageController {

    private final SimpMessagingTemplate template; //특정 broker로 매제지 전달, STOMP 프로토콜을 통해 메시지를 브로커로 전달한다.
    private final DirectMessageService dmService;


    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(DirectMessageRequest message){
//        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub
//
//        /chat/room/" + message.getRoomId(), message);
    }

    /**
     * 클라이언트가 메시지를 전송할때 호출되며, 수신된 메시지를 처리하고 브로커를 통해 해당 채팅방에 미시지를 브로드 캐스트 한다.
     */
    @MessageMapping(value = "/chat/message")
    public void message(DirectMessageRequest dmDto){
        System.out.println(dmDto.getMessage());
        System.out.println(dmDto.getSenderId());
        System.out.println(dmDto.getReceiverId());
        System.out.println(dmDto.getRoomId());
        dmService.sendDM(dmDto);
        template.convertAndSend("/sub/chat/room/" + dmDto.getRoomId(), dmDto);
    }

//    @MessageMapping(value = "/dm/message")
//    public void directMessage(DirectMessageRequest dmDto){
//        template.convertAndSend("/sub/chat/room/" + dmDto.getRoomId(), dmDto.getMessage());
//        dmService.sendDM(dmDto);
//    }
}

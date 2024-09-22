package com.chatting.controller;

import com.chatting.service.DirectMessageService;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import com.core.chat_core.chat.model.DirectMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DirectMessageApiController {

    private final DirectMessageService dmService;

    @PostMapping("/dm")
    public void sendDm(@RequestBody DirectMessageRequest directMessageDto) throws IllegalAccessException {
        dmService.sendDM(directMessageDto);
    }

//    @GetMapping("/dm")
//    public List<DirectMessageResponse> getDmLogs(@RequestParam Long user1Id, @RequestParam Long user2Id) {
//        return dmService.findRecentChatLog(user1Id, user2Id);
//    }

    /**
     * 두 사용자가 주고 받은 채팅 정보 모두 조회
     */
    @GetMapping("/dm/history")
    public List<DirectMessageResponse> getDmHistories(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return dmService.findChatHistory(user1Id, user2Id);
    }

    /**
     * 두 사용자가 주고 받은 마지막 채팅 정보 조회
     */
    @GetMapping("/dm/last/information")
    public DirectMessage findLastMessage(@RequestParam long user1Id, @RequestParam long user2Id){
        DirectMessage lastMessage = dmService.getLastMessage(user1Id, user2Id);
        return lastMessage;
    }

}

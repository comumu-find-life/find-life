package com.chatting.controller;

import com.chatting.service.DirectMessageService;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
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
    public void sendDm(@RequestBody DirectMessageRequest directMessageDto) {
        log.info(directMessageDto.getMessage());
        dmService.sendDM(directMessageDto);
    }

    @GetMapping("/dm")
    public List<DirectMessageResponse> getDmLogs(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return dmService.findRecentChatLog(user1Id, user2Id);
    }

    @GetMapping("/dm/history")
    public List<DirectMessageResponse> getDmHistories(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return dmService.findChatHistory(user1Id, user2Id);
    }

}

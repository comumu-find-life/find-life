package com.chatting.controller;

import com.chatting.dto.DirectMessageDto;
import com.chatting.dto.DirectMessageResponse;
import com.chatting.model.DirectMessage;
import com.chatting.service.DirectMessageService;
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
    public void sendDm(@RequestBody DirectMessageDto directMessageDto) {
        log.info(directMessageDto.getMessage());
        dmService.sendDM(directMessageDto);
    }

    @GetMapping("/dm")
    public List<DirectMessageResponse> getDmLogs(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return dmService.findRecentChatLog(user1Id, user2Id);
    }

    @GetMapping("/dm/history")
    public List<DirectMessageDto> getDmHistories(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return dmService.findChatHistory(user1Id, user2Id);
    }

}

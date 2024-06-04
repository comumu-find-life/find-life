package com.chatting.controller;

import com.chatting.dto.DirectMessageDto;
import com.chatting.service.DirectMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

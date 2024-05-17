package com.chatting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ChatTestController {

    @GetMapping("/dm")
    public String dmPage() {
        log.info("dm");
        return "chat/dmRoom";
    }
}

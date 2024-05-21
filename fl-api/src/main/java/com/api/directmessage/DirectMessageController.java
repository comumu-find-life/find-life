package com.api.directmessage;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.service.chat.DirectMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/api/dm")
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService dmService;

    @PostMapping("/dm")
    public void sendDm(@RequestBody DirectMessageApplicationDto dmDto) {

        dmService.applicationDm(dmDto);
    }

    @GetMapping("/dm-room")
    public void findDmRooms() {
        dmService.findDmRoomByLoginUserId();
    }
}

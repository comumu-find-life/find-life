package com.api.directmessage;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.service.chat.DirectMessageService;
import com.service.chat.dto.DirectMessageRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/dm")
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService dmService;

    @PostMapping()
    public void sendDm(@RequestBody DirectMessageApplicationDto dmDto) {

        dmService.applicationDm(dmDto);
    }

    @GetMapping("/dm-rooms")
    public ResponseEntity<List<DirectMessageRoomDto>> findDmRooms() {
        return ResponseEntity.ok(dmService.findDmRoomsByLoginUserId());
    }
}

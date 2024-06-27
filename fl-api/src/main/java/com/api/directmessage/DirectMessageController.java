package com.api.directmessage;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.core.chat.dto.DirectMessageRoomInfoDto;
import com.service.chat.DirectMessageService;
import com.service.chat.dto.DirectMessageRoomListResponse;
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
    public ResponseEntity<Boolean> sendDm(@RequestBody DirectMessageApplicationDto dmDto) {
        return ResponseEntity.ok(dmService.applicationDm(dmDto));
    }

    /**
     * 채팅방 목록
     * todo
     */
    @GetMapping("/dm-rooms")
    public ResponseEntity<List<DirectMessageRoomListResponse>> findDmRooms() {
        return ResponseEntity.ok(dmService.findDmRoomsByLoginUserId());
    }

    /**
     * 채팅 정보 조회
     * @param dmRoomId
     * @return
     */
    // 접근성 - Authenticated();
    @GetMapping("/dm-rooms/{dmRoomId}")
    public ResponseEntity<DirectMessageRoomInfoDto> findDmRoomInfo(@PathVariable Long dmRoomId) {
        return ResponseEntity.ok(dmService.findDmRoomById(dmRoomId));
    }
}

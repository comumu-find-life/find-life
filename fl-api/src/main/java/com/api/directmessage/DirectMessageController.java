package com.api.directmessage;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.core.chat.dto.DirectMessageRoomInfoDto;
import com.service.chat.DirectMessageRoomService;
import com.service.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/dm")
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageRoomService dmService;

    @PostMapping()
    public ResponseEntity<?> sendDm(@RequestBody DirectMessageApplicationDto dmDto) {
        Long roomId = dmService.applicationDm(dmDto);
        SuccessResponse response = new SuccessResponse(true, "채팅방 생성 성공", roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
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

package com.api.directmessage;

import com.common.chat.request.DirectMessageApplicationRequest;
import com.common.chat.response.DirectMessageRoomInfoResponse;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.service.chat.DirectMessageRoomService;
import com.common.utils.SuccessResponse;
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
    public ResponseEntity<?> sendDm(@RequestBody DirectMessageApplicationRequest dmDto) {
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
    public ResponseEntity<DirectMessageRoomInfoResponse> findDmRoomInfo(@PathVariable Long dmRoomId) {
        return ResponseEntity.ok(dmService.findDmRoomById(dmRoomId));
    }

    /**
     * 사용자 모든 채팅 목록 조회
     */
    @GetMapping("/dm-rooms")
    public ResponseEntity<?> findAllDmRooms(){
        List<DirectMessageRoomListResponse> dmRoomsByLoginUserId = dmService.findDmRoomsByLoginUserId();
        SuccessResponse response = new SuccessResponse(true, "사용자 채팅 목록 조회 성공", dmRoomsByLoginUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

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

import static com.api.config.ApiUrlConstants.*;
import static com.api.directmessage.SuccessDirectMessages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageRoomService dmService;

    /**
     * 첫 채팅 전송 API
     */
    @PostMapping(DM_BASE_URL)
    public ResponseEntity<?> sendDm(@RequestBody DirectMessageApplicationRequest dmDto) {
        Long roomId = dmService.applicationDm(dmDto);
        SuccessResponse response = new SuccessResponse(true, DM_ROOM_CREATE_MESSAGE, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DM_FIND_ROOM_INFO)
    public ResponseEntity<DirectMessageRoomInfoResponse> findDmRoomInfo(@PathVariable Long dmRoomId) {
        return ResponseEntity.ok(dmService.findDmRoomById(dmRoomId));
    }

    /**
     * 자신의 모든 채팅 목록 조회
     */
    @GetMapping(DM_FIND_ALL_ROOMS)
    public ResponseEntity<?> findAllDmRooms() {
        List<DirectMessageRoomListResponse> dmRoomsByLoginUserId = dmService.findDmRoomsByLoginUserId();
        SuccessResponse response = new SuccessResponse(true, DM_LIST_FIND_MESSAGE, dmRoomsByLoginUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

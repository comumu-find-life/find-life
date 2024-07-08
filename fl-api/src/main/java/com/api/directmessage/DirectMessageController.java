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

import static com.api.directmessage.SuccessDirectMessages.DM_LIST_FIND_MESSAGE;
import static com.api.directmessage.SuccessDirectMessages.DM_ROOM_CREATE_MESSAGE;

@Slf4j
@RestController
@RequestMapping("/v1/api/dm")
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageRoomService dmService;

    @PostMapping()
    public ResponseEntity<?> sendDm(@RequestBody DirectMessageApplicationRequest dmDto) {
        Long roomId = dmService.applicationDm(dmDto);
        SuccessResponse response = new SuccessResponse(true, DM_ROOM_CREATE_MESSAGE, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 채팅 정보 조회 todo 안전거래 채팅은 제외시키는 기능 구현
     * @param dmRoomId
     * @return
     */
    // 접근성 - Authenticated();
    @GetMapping("/dm-rooms/{dmRoomId}")
    public ResponseEntity<DirectMessageRoomInfoResponse> findDmRoomInfo(@PathVariable Long dmRoomId) {
        return ResponseEntity.ok(dmService.findDmRoomById(dmRoomId));
    }

    /**
     * 사용자 모든 채팅 목록 조회 todo 안전거래 채팅은 제외시키는 기능 구현
     */
    @GetMapping("/dm-rooms")
    public ResponseEntity<?> findAllDmRooms(){
        List<DirectMessageRoomListResponse> dmRoomsByLoginUserId = dmService.findDmRoomsByLoginUserId();
        SuccessResponse response = new SuccessResponse(true, DM_LIST_FIND_MESSAGE, dmRoomsByLoginUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
